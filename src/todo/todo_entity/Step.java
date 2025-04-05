package todo_entity;

import db1.Entity;

public class Step extends Entity{
    private final int STEP_ENTITY_CODE = 8;
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

       return stepCopy;
    }

    @Override
    public int getEntityCode() {
        return STEP_ENTITY_CODE;
    }
}
