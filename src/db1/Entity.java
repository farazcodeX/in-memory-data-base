package db1;

public abstract class Entity {
    private static int idCounter = 0;

    public Entity() { id = ++idCounter; }
    public int id;
    public abstract Entity copy();
    public abstract int getEntityCode();
}