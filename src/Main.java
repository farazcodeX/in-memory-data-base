import java.util.Scanner;

import db1.Database;
import dbException.EntityNotFoundException;
import dbException.InvalidEntityException;
import example.Document;
import example.Human;
import example.HumanValidator;
import todo_entity.Step;
import todo_entity.Task;
import todo_service.StepService;
import todo_service.TaskService;
import todo_validator.StepValidator;
import todo_validator.TaskValidator;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Database.registerValidator(Task.TASK_ENTITY_CODE, new TaskValidator());
        Database.registerValidator(Step.STEP_ENTITY_CODE, new StepValidator());

        // lets begin 
        while (true) {
            System.out.println("choose an item : add task / add step / delete task / delete step / update task / update step / get task / exit");
            String choise = scanner.nextLine().trim().toLowerCase();
            switch (choise) {
                case "add task" : TaskService.addTask(); break;
                case "add step" : StepService.addStep(); break;
                case "delete task" : TaskService.deleteTask(); break;
                case "delete step" : StepService.deleteStep(); break;
                case "update task" : TaskService.updateTask(); break;
                case "update step" : StepService.updateStep(); break;
                case "get task" : TaskService.getTaskById(); break;
                case "exit" : break;
                default : System.out.println("inputed order is invalid"); continue;

            }
            
        }





    }


}