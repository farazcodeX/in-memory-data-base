package todo_entity;

import java.util.Date;

import db1.Entity;
import db1.Trackable;

public class Step extends Entity implements Trackable{

    private Date creationDate;
    private Date lastModificationDate;        
    public static final int STEP_ENTITY_CODE = 8;
    public String title;
    public Status status;
    // task id
    public int taskRef;

    public Step(String name, Status status, int taskRef) {
        this.taskRef = taskRef;
        this.status = status;
        this.title = name;
    }

    public enum Status {
        NoStarted, Completed
    }

    @Override
    public Entity copy() {
        Step stepCopy = new Step(title, status, taskRef);
        stepCopy.id = id;

       return stepCopy;
    }

    @Override
    public int getEntityCode() {
        return STEP_ENTITY_CODE;
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
}
