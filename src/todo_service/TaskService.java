package todo_service;

import java.nio.file.StandardOpenOption;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import db1.Database;
import db1.Entity;
import dbException.EntityNotFoundException;
import dbException.InvalidEntityException;
import todo_entity.Step;
import todo_entity.Task;

public class TaskService {

    static Scanner scanner = new Scanner(System.in);

    public enum Condition {
        all, incompelete
    }

    public static void setAsCompleted(int taskId) {
        Entity entity = null;
        try {
             entity = Database.get(taskId);
        } catch (EntityNotFoundException e) {
            System.out.println("No Entity found with : ID : " + taskId);
            return;
        }
        
        if (!(entity instanceof Task)) {
            System.out.println("Error: The ID does not correspond to a Task.");
            return;
        }
        Task task = (Task) entity;
        task.status = Task.Status.Completed;
        try {
            Database.update(task);
            System.out.println("Task : " + task.title + "  ID : " + task.id + "  : marked as completed 100%");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: Task not found during update.");
        } catch (InvalidEntityException e) {
      
        }
    }

    public static void addTask() {
        System.out.println("----------------------------");
        System.out.print("Enter Task name: ");
        String title = scanner.nextLine();
        System.out.print("Enter Task description: ");
        String description = scanner.nextLine();
        // lets get the due date
        System.out.println("Enter due Date in (xxxx-yy-zz) format");
        String dueDateInput = scanner.nextLine();
        Date dueDate = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formatter.setLenient(false);
            java.util.Date parsedDate = formatter.parse(dueDateInput);
            dueDate = new Date(parsedDate.getTime()); 
        } catch (ParseException e) {
            System.out.println("SomeThing went wrong : possible issue : Invalid date format provided");
            return;

        }

        
        Task task = new Task(title, description, dueDate, Task.Status.NoStarted);

        try {
            Database.add(task);
            System.out.println("Task added successfully!");
        } catch (InvalidEntityException e) {
            System.out.println("Cannot save Task. Possible issues with the Task entity.");
        }
    }

    public static void updateTask() {
        System.out.println("----------------------------");
        System.out.print("Enter Task ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Entity entity = null;
        try {
            entity = Database.get(id);
        } catch (EntityNotFoundException e) {
            System.out.println("provided ID is not belong to a Task");
            return;
        } 

        
        if (!(entity instanceof Task)) {
            System.out.println("Invalid ID provided. Entity is not a Task.");
            return;
        }

        Task task = (Task) entity;
        Boolean update = false;

        while (true) {
            update = false;
            System.out.println("Enter field to update: title / description / dueDate / status / exit");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "title":
                    System.out.print("Enter new title: ");
                    task.title = scanner.nextLine();
                    update = true;
                    break;
                case "description":
                    System.out.print("Enter new description: ");
                    task.description = scanner.nextLine();
                    update = true;
                    break;
                case "status":
                    System.out.print("Enter new status (NoStarted / InProgres / Complete): ");
                    String statusInput = scanner.nextLine().trim().toLowerCase();
                    switch (statusInput) {
                        case "nostarted":
                            task.status = Task.Status.NoStarted;
                            update = true;
                            break;
                        case "inprogres":
                            task.status = Task.Status.InProgres;
                            update = true;
                            break;
                        case "complete":
                            task.status = Task.Status.Completed;
                            // lets make all of tasks complete
                            setAsCompletedAllSteps(task.id);
                            update = true;
                            break;
                        default:
                            System.out.println("Invalid status input.");
                            continue;
                    }
                    break;
                case "dueDate":
                    System.out.print("Enter new due date (yyyy-MM-dd): ");
                    String dateStr = scanner.nextLine();
                    try {
                        LocalDate localDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        task.dueDate = Date.valueOf(localDate);
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format.");
                        continue;
                    }
                    update = true;
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Unknown field.");
                    continue;
            }

            if(update) {
                System.out.println("Comfirm change ? (yes) / (NO)");
                choice = scanner.nextLine().trim().toLowerCase();

                if(choice.equals("yes")) {
                    try {
                        Database.update(task);
                        System.out.println("Task updated successfully.");
                    } catch (EntityNotFoundException e) {
                        System.out.println("Update failed: Task not found.");
                    } catch(InvalidEntityException e) {
                        System.out.println("Update failed");
                    } 
                } else {
                    System.out.println("update canceiled");
                }   
            }
        }
    }
    public static void getTaskById() {
        System.out.println("-----------------");
        System.out.print("Enter Task ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
    
        Task task;
        ArrayList<Step> steps = null;
    
        try {
            Entity entity = Database.get(id);
            if (!(entity instanceof Task)) {
                System.out.println("Entity found is not a Task.");
                return;
            }
            task = (Task) entity;
        } catch (EntityNotFoundException e) {
            System.out.println("Cannot find task with ID: " + id);
            return;
        }
    
        try {
            steps = Database.getStepsOfTask(id);
        } catch (EntityNotFoundException e) {
            steps = new ArrayList<>(); 
        }
    
        
        System.out.println("Task Details:");
        System.out.println("-------------");
        System.out.println("ID       : " + id);
        System.out.println("Title    : " + task.title);
        System.out.println("Due Date : " + task.dueDate);
        System.out.println("Status   : " + task.status);
    
        
        if (steps.isEmpty()) {
            System.out.println("- No steps available for this task.");
        } else {
            System.out.println("Steps:");
            for (Step step : steps) {
                System.out.println("+ " + step.title);
                System.out.println("   ID     : " + step.id);
                System.out.println("   Status : " + step.status);
            }
        }
    }
    public static void deleteTask() {

        System.out.println("---------------");
        System.out.print("Enter Task ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
    
        Entity entity;
    
        try {
            entity = Database.get(id);
        } catch (EntityNotFoundException e) {
            System.out.println("Error: No entity found with ID " + id);
            return;
        }
    
        if (!(entity instanceof Task)) {
            System.out.println("Error: The ID does not correspond to a Task.");
            return;
        }
    
        try {
            Database.delete(id);
            System.out.println("Task deleted successfully.");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: Failed to delete Task. Entity not found.");
        }
    }

    public static void getAllTaks(Condition condition) {
        ArrayList<Task> tasks = null;
        ArrayList<Step> steps;
        Boolean hasStep = false;
        try {
            tasks = Database.getAllTaks();

        } catch (EntityNotFoundException e) {
            System.out.println("No Tasks to show");
            return;
        }
        System.out.println("All Tasks : ");
        for(Task task : tasks) {
            if(condition == condition.incompelete) {
                if(task.status == Task.Status.Completed) {
                    continue;
                }
            }
            hasStep = true;
            try {
                steps = Database.getStepsOfTask(task.id);
            } catch (EntityNotFoundException e) {
                hasStep = false;
                steps = new ArrayList<>();
            }
            System.out.println("---------------");
            System.out.println("ID       : " + task.id);
            System.out.println("Title    : " + task.title);
            System.out.println("Due Date : " + task.dueDate);
            System.out.println("Status   : " + task.status);
            if(hasStep) {
                System.out.println("Steps:");
                for (Step step : steps) {
                    System.out.println("+ " + step.title);
                    System.out.println("   ID     : " + step.id);
                    System.out.println("   Status : " + step.status);       
                }
            } else {
                System.out.println("    NO steps");
            }
        }
    }
    public static void getInCompeleteTasks() {
        getAllTaks(Condition.incompelete);
    }

    public static void setAsCompletedAllSteps(int id) {
        ArrayList<Step> steps = null;
        try {
             steps = Database.getStepsOfTask(id);
        } catch(EntityNotFoundException e) {
            steps = new ArrayList<>();
        }

        if(steps.isEmpty()) {
            System.out.println("This task has no steps.");
            return;
        } 
        
            int stepCounter = 1;
            for(Step step : steps) {

                if(step.status != Step.Status.Completed) {
                    // we cahnge and update status here 
                    step.status = Step.Status.Completed;
                    try {
                        System.out.println("updating step : ID " + step.id);
                        Database.update(step);
                        System.out.println("Step : " + stepCounter + " Completed successFully");
                        ++stepCounter;
                    } catch (EntityNotFoundException | InvalidEntityException e) {
                        System.out.println("Failed to apply update");
                        return;

                    }
                }
            }
            System.out.println("All steps have been marked as Completed. 100%");
    }
}
