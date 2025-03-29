package db1;

import java.util.ArrayList;
import java.util.List;
import dbException.*;



public final class Database {
    
    private static List<Entity> entities = new ArrayList<>();
    public static int idCounter = 1;

    private Database() {
       //"Making a class constructor private will prevent the class from being instantiated."
      // classes like Math in java is like this
        
    }
    public static void add(Entity entity) {
        
        entity.id = idCounter;

        ++idCounter;

        entities.add(entity);
    }
    public static Entity get(int id) throws EntityNotFoundException {
        for(Entity entity : entities) {
            if(entity.id == id) {
                return entity;
            }
        }
        // if entity not found
        throw new EntityNotFoundException();
        
    }
    public static void delete(int id) throws EntityNotFoundException {

        for(Entity entity : entities) {
            if(entity.id == id) {
                entities.remove(entity);
                return;
            }
        }
        // if not
        throw new EntityNotFoundException(id);


    }
    // i dont recommend this update (your way)
    public static void update(Entity e) throws EntityNotFoundException {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).id == e.id) {
                entities.set(i, e); 
                return; 
            }
        }

        // if not found
       throw new EntityNotFoundException();
      
        
    }


    
}
