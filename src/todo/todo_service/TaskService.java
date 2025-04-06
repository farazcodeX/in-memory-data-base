package todo_service;

import java.sql.Date;
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

    public static void setAsCompleted(int taskId) throws InvalidEntityException {
        Entity entity = Database.get(taskId);
        if (!(entity instanceof Task)) {
            System.out.println("Error: The ID does not correspond to a Task.");
            return;
        }
        Task task = (Task) entity;
        task.status = Task.Status.Completed;
        try {
            Database.update(task);
            System.out.println("Task marked as completed.");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: Task not found during update.");
        }
    }

    public static void addTask() {
        System.out.println("----------------------------");
        System.out.print("Enter Task name: ");
        String title = scanner.nextLine();
        System.out.print("Enter Task description: ");
        String description = scanner.nextLine();

        Date date = Date.valueOf(LocalDate.now()); // current date
        Task task = new Task(title, description, date, Task.Status.NoStarted);

        try {
            Database.add(task);
            System.out.println("Task added successfully!");
        } catch (InvalidEntityException e) {
            System.out.println("Cannot save Task. Possible issues with the Task entity.");
        }
    }

    public static void updateTask() throws InvalidEntityException {
        System.out.println("----------------------------");
        System.out.print("Enter Task ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Entity entity = Database.get(id);
        if (!(entity instanceof Task)) {
            throw new InvalidEntityException("Invalid ID provided. Entity is not a Task.");
        }

        Task task = (Task) entity;

        while (true) {
            System.out.println("Enter field to update: title / description / dueDate / status / exit");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "title":
                    System.out.print("Enter new title: ");
                    task.title = scanner.nextLine();
                    break;
                case "description":
                    System.out.print("Enter new description: ");
                    task.description = scanner.nextLine();
                    break;
                case "status":
                    System.out.print("Enter new status (NoStarted / InProgres / Completed): ");
                    String statusInput = scanner.nextLine();
                    switch (statusInput) {
                        case "NoStarted":
                            task.status = Task.Status.NoStarted;
                            break;
                        case "InProgres":
                            task.status = Task.Status.InProgres;
                            break;
                        case "Completed":
                            task.status = Task.Status.Completed;
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
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Unknown field.");
                    continue;
            }

            try {
                Database.update(task);
                System.out.println("Task updated successfully.");
            } catch (EntityNotFoundException e) {
                System.out.println("Update failed: Task not found.");
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
    public static void getAllTaks() {
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
    
}
