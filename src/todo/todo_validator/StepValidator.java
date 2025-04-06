package todo_validator;

import java.sql.Date;

import db1.Entity;
import db1.Trackable;
import db1.Validator;
import dbException.EntityNotFoundException;
import dbException.InvalidEntityException;
import todo_entity.Step;
import db1.Database;

public class StepValidator implements Validator{

    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if(!(entity instanceof Step)) {
            throw new InvalidEntityException("Invalid entity inputed");
        }
        // down Cast
        Step step = (Step)entity;
        if(step.title.isEmpty()) {
            throw new InvalidEntityException("Step title is empty ");

        }
        try {
            // it should returns a task if it dont theres no task 
            Database.get(step.taskRef);
        } catch(EntityNotFoundException e) {
            throw new InvalidEntityException("Invalid Task ID inputed");
        }
        
        
    }
}
