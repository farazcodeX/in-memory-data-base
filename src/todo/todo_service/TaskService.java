package todo_service;

import db1.Database;
import db1.Entity;
import dbException.InvalidEntityException;
import todo_entity.Step;
import todo_entity.Task;
import db1.Database;

public class TaskService {
    public static void setAsCompleted(int taskId) throws InvalidEntityException {
        Entity entity = Database.get(taskId);
        // if entity with this ID isnt found get method can handle
        // we need to check if this is task or no
        if(!(entity instanceof Task)) {
            throw new InvalidEntityException("entity is not task"); 
        }

        Task task = (Task)entity;
        task.status = Task.Status.Completed;

    }
}
