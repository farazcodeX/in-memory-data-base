package todo_entity;

import java.sql.Date;

import db1.Entity;
import db1.Trackable;

public class Task extends Entity implements Trackable {

    private Date creationDate;
    private Date lastModificationDate;
    public static final int TASK_ENTITY_CODE = 30;

    public Status status;
    public String title;
    public String description;
    public Date dueDate;

    public Task(String title, String des, Date date, Status status) {
        this.title = title;
        this.description = des;
        this.dueDate = date;
        this.status = status;

    }


    public enum Status {
        NoStarted, InProgres, Completed
    }

    @Override
    public void setCreationDate(Date date) {
        creationDate = date;
    }

    @Override
    public Date getCreationDate() {
            return creationDate;
    }

    @Override
    public void setLastModificationDate(Date date) {
        lastModificationDate = date;
    }

    @Override
    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    @Override
    public Task copy() {
        Task taskCopy = new Task(title, description, creationDate, status);
        taskCopy.id = id;
        if(creationDate != null) {
            taskCopy.setCreationDate(new Date(this.creationDate.getTime()));
             if(lastModificationDate != null) {
                taskCopy.setLastModificationDate(new Date(this.lastModificationDate.getTime()));
             }
        }
        return taskCopy;
        
    }

    @Override
    public int getEntityCode() {
        return TASK_ENTITY_CODE;
    }
   
    
}
