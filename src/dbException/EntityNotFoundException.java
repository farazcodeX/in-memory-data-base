package dbException;
public class EntityNotFoundException extends Exception{

    public EntityNotFoundException() {
        super("entity not found");
    } 
    public EntityNotFoundException(String massage) {
        super(massage);
    }
    public EntityNotFoundException(int id) {
        super("cannot find entity with id = {"+id+"}");
    }
}
    

