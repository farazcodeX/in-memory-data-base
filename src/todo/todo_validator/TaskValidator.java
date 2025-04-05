package todo_validator;

import db1.Entity;
import db1.Validator;
import dbException.InvalidEntityException;
import todo_entity.Step;
import todo_entity.Task;

public class TaskValidator implements Validator{

    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if(!(entity instanceof Step)) {
            throw new InvalidEntityException("Invalid entity inputed");
        }
        // down Cast
        Step step = (Step)entity;
        if(step.title.isEmpty()) {
            throw new InvalidEntityException("Invalid Step entity");

        }
        // checking taskref
     }
    
}
