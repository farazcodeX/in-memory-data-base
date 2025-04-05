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
    public Entity copy() {
        return null;

    }

    @Override
    public int getEntityCode() {
        return TASK_ENTITY_CODE;
    }
   
    
}
