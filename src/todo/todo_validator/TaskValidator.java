package todo_validator;

import db1.Entity;
import db1.Validator;
import dbException.InvalidEntityException;
import todo_entity.Step;
import todo_entity.Task;

public class TaskValidator implements Validator{

    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if(!(entity instanceof Task)) {
            throw new InvalidEntityException("Invalid entity inputed");
        }
        // down Cast
        Task task = (Task)entity;
        if(task.title.isEmpty()) {
            throw new InvalidEntityException("Invalid Step entity");

        }
        

     }
    
}
