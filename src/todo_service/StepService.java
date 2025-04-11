package todo_service;

import java.security.KeyStore.Entry;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import db1.Database;
import db1.Entity;
import dbException.EntityNotFoundException;
import dbException.InvalidEntityException;
import todo_entity.Step;
import todo_entity.Task;

public class StepService {
    
    private static Scanner scanner = new Scanner(System.in);

    public static void addStep() {
        System.out.println("----------------");
        System.out.println("Enter Task Id  :");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Step title : ");
        String title = scanner.nextLine();

        // lets check id
        Entity entity = null;
        try {
            entity = Database.get(id);
        } catch (EntityNotFoundException e) {
            System.out.println("no Entity with ID : " + id + " not found");
            return;
        }
        if(!(entity instanceof Task)) {
            System.out.println("ID : : " + id + " doese not belong to a Task.");
            return;
        }

        Step step = new Step(title, Step.Status.NoStarted, id);

        try {
            Database.add(step);
            System.out.println("-------------------------");
            System.out.println("Step added successfully");
            System.out.println("ID : " + step.id);
            System.out.println("Creation Date : " + step.getCreationDate());
            
        } catch (InvalidEntityException e) {
            System.out.println("Cannot Save Step : Possible issues : Invalid TaskID / empy title");   
        }
    }

    public static void deleteStep() {
        System.out.println("------------------");
        System.out.println("Enter step ID");
        int id = scanner.nextInt();
        scanner.nextLine();

        // lets check the id
        Entity entity;
    
        try {
            entity = Database.get(id);
        } catch (EntityNotFoundException e) {
            System.out.println("Error: No entity found with ID " + id);
            return;
        }
    
        if (!(entity instanceof Step)) {
            System.out.println("Error: The ID does not correspond to a Step.");
            return;
        }

        try {
            Database.delete(id);
            System.out.println("Step deleted successfully.");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: Failed to delete Step :  Entity not found.");
        }
    }

    
    public static void updateStep() {
        System.out.println("\n----------------------------");
        System.out.print("Enter Step ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline
    
        Entity entity;
        try {
            entity = Database.get(id);
        } catch (EntityNotFoundException e) {
            System.out.println("Entity not found: Invalid ID " + id);
            return;
        }
    
        if (!(entity instanceof Step)) {
            System.out.println(" Invalid ID provided. Entity is not a Step.");
            return;
        }
    
        Step step = (Step) entity;
        List<String> changes = new ArrayList<>();

    
        while (true) {
            System.out.println("\nChoose a field to update:");
            System.out.println("→ title / status / task id / exit");
            System.out.print("> ");
            String choice = scanner.nextLine().trim().toLowerCase();
    
            boolean update = false;
            Boolean completeChange = false;
    
            switch (choice) {
                case "title":
                    changes.add("Old title : " + step.title);
                    System.out.print("Enter new title: ");
                    step.title = scanner.nextLine().trim();
                    changes.add("New Title : " + step.title);
                    update = true;
                    break;

                    case "status":
                    changes.add("Old status : " + step.status.name());
                    System.out.print("Enter new status (NotStarted / Completed): ");
                    String status = scanner.nextLine().trim().toLowerCase();
                
                    switch (status) {
                        case "notstarted":
                            if (step.status != Step.Status.NoStarted) {
                                changes.add("new Status : " + status);
                                step.status = Step.Status.NoStarted;
                                update = true;
                            } else {
                                System.out.println("Step is already not started : try other changes");
                            }
                            break;
                
                        case "completed":
                            if (step.status != Step.Status.Completed) {
                                step.status = Step.Status.Completed;
                                changes.add("new Status : " + status);
                                completeChange = true;
                                update = true;
                            } else {
                                System.out.println("Step is already completed");
                            }
                            break;
                
                        default:
                            System.out.println("Invalid status input.");
                            break;
                    }
                    break;
                
                case "task id":
                    changes.add("Old Task reference :" + step.taskRef);
                    System.out.print("Enter new Task ID: ");
                    int taskID = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    changes.add("New Task reference : " + step.taskRef);
    
                    Entity taskEntity;
                    try {
                        taskEntity = Database.get(taskID);
                    } catch (EntityNotFoundException e) {
                        System.out.println(" Task not found: Invalid ID.");
                        break;
                    }
    
                    if (!(taskEntity instanceof Task)) {
                        System.out.println(" Invalid ID: id is not belong to a Task");
                    } else {
                        step.taskRef = taskID;
                        update = true;
                    }
                    break;
    
                case "exit":
                    System.out.println(" Exiting Step update.");
                    return;
    
                default:
                    System.out.println(" Unknown option. Try again.");
                    break;
            }
    
            if (update) {
                System.out.print(" Confirm update? (yes/no): ");
                String confirm = scanner.nextLine().trim().toLowerCase();
    
                if (confirm.equals("yes")) {
                    try {
                        Database.update(step);
                        // lets check if all of steps are complete or no
                        taskCompleteChecker(step.taskRef);
                        System.out.println(" Step updated successfully.");
                        System.out.println("ID :  " + step.id);
                        if(!changes.isEmpty()) {
                            System.out.println(changes.get(0));
                            System.out.println(changes.get(1));
                        }
                        System.out.println(step.getLastModificationDate());
                        
                    } catch (EntityNotFoundException e) {
                        System.out.println(" Update failed: Step not found.");
                        return;
                    } catch (InvalidEntityException e) {
                        System.out.println(" Update failed: " + e.getMessage());
                        return;
                    }
                } else {
                    System.out.println(" Update cancelled.");
                    changes.clear();
                }
            }
        }
    }

    public static void taskCompleteChecker(int taskRef) {
        Boolean allComplte = true;
        ArrayList<Step> steps = new ArrayList<>();
        try {
            steps = Database.getStepsOfTask(taskRef);
        } catch (EntityNotFoundException e) {
            return;
        } 
        if(!steps.isEmpty()) {
            for(int i = 0; i < steps.size(); ++i) {
                if(steps.get(i).status != Step.Status.Completed) {
                    allComplte = false;
                    break;
                }
            }
            if(allComplte) {
                TaskService.setAsCompleted(taskRef);
            }
        }
    }
    public static void makeTaskInProgres(int taskid) {
        Entity entity = null;
        try {
            entity = Database.get(taskid);
        } catch (EntityNotFoundException e) {
            System.out.println("Unknown erroe in proccess : entity : Task not found");
            return;
        }
        if(!(entity instanceof Task)) {
            System.out.println("ID : " + taskid + "  does not belong to a Task.");
            return;
        }
        Task task = (Task)entity;
        if(task.status == Task.Status.NoStarted) {
            task.status = Task.Status.InProgres;
            // update
            try {
                Database.update(task);
                System.out.println("Task + " + task.id + "  has been changed to inprogress .");
            } catch (EntityNotFoundException | InvalidEntityException e) {
               System.out.println("unkown erroe");
            }
        }
    }
}
    