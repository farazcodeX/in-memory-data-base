package todo_entity;

import db1.Entity;

public class Step extends Entity{
    public String title;
    public Status status;
    // task id
    public int taskRef;

    public enum Status {
        NoStarted, Completed
    }

    @Override
    public Entity copy() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'copy'");
    }

    @Override
    public int getEntityCode() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEntityCode'");
    }
}
