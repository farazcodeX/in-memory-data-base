package db1;

import java.util.ArrayList;
import java.lang.annotation.ElementType;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import dbException.*;
import todo_entity.Step;



public final class Database {
    
    private static List<Entity> entities = new ArrayList<>();
    private static HashMap<Integer, Validator> validators;
    private static int idCounter = 0;

    private Database() {
       //"Making a class constructor private will prevent the class from being instantiated."
      // classes like Math in java is like this

    }
    public static void add(Entity entity) throws InvalidEntityException { 

        Validator validator = validators.get(entity.getEntityCode());
        if(validator == null) {
            throw new InvalidEntityException("No validator found for this Entity COde : " + entity.getEntityCode() + "\nPossible issue : Validator not added ");

        }
    
        validator.validate(entity);

        if(entity instanceof Trackable) {
            Date date = new Date(System.currentTimeMillis());
            ((Trackable) entity).setCreationDate(date);
            ((Trackable) entity).setLastModificationDate(date);
        }
        entity.id = ++idCounter;
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
    // i dont recommend this update (your way)
    public static void update(Entity e) throws EntityNotFoundException {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).id == e.id) {
                entities.set(i, e.copy()); 

                if(e instanceof Trackable) {
                    Date date = new Date(System.currentTimeMillis());
                    ((Trackable) e).setLastModificationDate(date);
                }
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
        // return all the entities with specified entityCode
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

}
