package todo_service;

import java.security.KeyStore.Entry;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

        Step step = new Step(title, Step.Status.NoStarted, id);

        try {
            Database.add(step);
            System.out.println("Step added successfully");
            
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
            System.out.println("❌ Entity not found: Invalid ID " + id);
            return;
        }
    
        if (!(entity instanceof Step)) {
            System.out.println("❌ Invalid ID provided. Entity is not a Step.");
            return;
        }
    
        Step step = (Step) entity;
    
        while (true) {
            System.out.println("\nChoose a field to update:");
            System.out.println("→ title / status / task id / exit");
            System.out.print("> ");
            String choice = scanner.nextLine().trim().toLowerCase();
    
            boolean update = false;
    
            switch (choice) {
                case "title":
                    System.out.print("Enter new title: ");
                    String newTitle = scanner.nextLine().trim();
    
                    if (newTitle.isEmpty()) {
                        System.out.println("⚠️ Title cannot be empty.");
                    } else {
                        step.title = newTitle;
                        update = true;
                    }
                    break;
    
                case "status":
                    System.out.print("Enter new status (NoStarted / Completed): ");
                    String statusInput = scanner.nextLine().trim();
    
                    if (statusInput.equalsIgnoreCase("NoStarted")) {
                        step.status = Step.Status.NoStarted;
                        update = true;
                    } else if (statusInput.equalsIgnoreCase("Completed")) {
                        step.status = Step.Status.Completed;
                        update = true;
                    } else {
                        System.out.println("⚠️ Invalid status input.");
                    }
                    break;
    
                case "task id":
                    System.out.print("Enter new Task ID: ");
                    int taskID = scanner.nextInt();
                    scanner.nextLine(); // consume newline
    
                    Entity taskEntity;
                    try {
                        taskEntity = Database.get(taskID);
                    } catch (EntityNotFoundException e) {
                        System.out.println("⚠️ Task not found: Invalid ID.");
                        break;
                    }
    
                    if (!(taskEntity instanceof Task)) {
                        System.out.println("⚠️ Invalid ID: Not a Task entity.");
                    } else {
                        step.taskRef = taskID;
                        update = true;
                    }
                    break;
    
                case "exit":
                    System.out.println("👋 Exiting Step update.");
                    return;
    
                default:
                    System.out.println("⚠️ Unknown option. Try again.");
                    break;
            }
    
            if (update) {
                System.out.print("✅ Confirm update? (yes/no): ");
                String confirm = scanner.nextLine().trim().toLowerCase();
    
                if (confirm.equals("yes") || confirm.equals("y")) {
                    try {
                        Database.update(step);
                        System.out.println("🎉 Step updated successfully.");
                    } catch (EntityNotFoundException e) {
                        System.out.println("❌ Update failed: Step not found.");
                        return;
                    } catch (InvalidEntityException e) {
                        System.out.println("❌ Update failed: " + e.getMessage());
                        return;
                    }
                } else {
                    System.out.println("❎ Update cancelled.");
                }
            }
        }
    }
}
    