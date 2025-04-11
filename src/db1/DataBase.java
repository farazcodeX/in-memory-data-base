package db1;

import java.util.ArrayList;
import java.lang.annotation.ElementType;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import dbException.*;
import todo_entity.Step;
import todo_entity.Task;
import todo_validator.TaskValidator;



public final class Database {
    
    private static List<Entity> entities = new ArrayList<>();
    private static HashMap<Integer, Validator> validators = new HashMap<>();
    private static int idCounter = 0;

    private Database() {}


    public static void add(Entity entity) throws InvalidEntityException { 
        
        Validator validator = validators.get(entity.getEntityCode());
        if(validator == null) {
            throw new InvalidEntityException("No validator found for this Entity COde : " + entity.getEntityCode() + "\nPossible issue : Validator not added ");
        }
        validator.validate(entity);

        if(entity instanceof Trackable) {
            Date currentDate = new Date(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+03:30"));
            String formattedDate = sdf.format(currentDate);
            try {
                
                ((Trackable) entity).setCreationDate(sdf.parse(formattedDate));
                ((Trackable) entity).setLastModificationDate(sdf.parse(formattedDate));
            } catch (ParseException e) {
                
            }

        }
        ++idCounter;
        entity.id = idCounter;
        entities.add(entity.copy());
    }
    public static Entity get(int id) throws EntityNotFoundException {
        for(Entity entity : entities) {
            if(entity.id == id) {
                return entity.copy();
            }
        }
        // if entity not found
        throw new EntityNotFoundException();
    }
    public static void delete(int id) throws EntityNotFoundException {
        for(Entity entity : entities) {
            if(entity.id == id) {
                entities.remove(entity);
                return;
            }
        }
        // if not
        throw new EntityNotFoundException(id);
    }
    
    public static void update(Entity entity) throws EntityNotFoundException, InvalidEntityException {

       Validator validator = validators.get(entity.getEntityCode());
        if(validator == null) {
            throw new InvalidEntityException("No validator found for this Entity COde : " + entity.getEntityCode() + "\nPossible issue : Validator not added ");
        }
        validator.validate(entity);

        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).id == entity.id) {
                 

                if(entity instanceof Trackable) {
                    Date currentDate = new Date(System.currentTimeMillis());
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
                    sdf.setTimeZone(TimeZone.getTimeZone("GMT+03:30"));
                    String formattedDate = sdf.format(currentDate);
                    try {
                        ((Trackable) entity).setLastModificationDate(sdf.parse(formattedDate));
                    } catch (ParseException e) {}
                }
                entities.set(i, entity.copy());
                return; 
            }
        }
        // if not found
       throw new EntityNotFoundException();
    }
    public static void registerValidator(int entityCode, Validator validator) {
        
        if (validators.containsKey(entityCode)) {
            throw new IllegalArgumentException("validator for entity code " + entityCode + " already exists.");
        } else {
             validators.put(entityCode, validator);
        } 
    }

    public static ArrayList<Entity> getAll(int entityCode) {
        
        ArrayList<Entity> newList = new ArrayList<>();
        for(Entity entity : entities) {
            if(entity.getEntityCode() == entityCode) {
                newList.add(entity.copy());
            }
        }
        if(!(newList.isEmpty())) {
            return newList;
        } else {
            throw new EntityNotFoundException();
        }  
    }
    // this is mine
    public static ArrayList<Step> getStepsOfTask(int taskRef) {

        ArrayList<Step> steps = new ArrayList<>();
        for(Entity entity : entities) {
            if(entity instanceof Step) {
                Step step = (Step)entity;
                if(step.taskRef == taskRef) {
                    steps.add(step);
                }
            }
        }
        if(!steps.isEmpty()) {
            return steps;
        } 
        throw new EntityNotFoundException();    
    }
    // also mine
   public static ArrayList<Task> getAllTaks() {
        ArrayList<Task> tasks = new ArrayList<>();
        for(Entity entity : entities) {
            if(entity instanceof Task) {
                Task task = (Task)entity;
                tasks.add(task);
            }
        }
        if(!tasks.isEmpty()) {
            return tasks;
        } else {
            throw new EntityNotFoundException();
        }    
    }
}
