package db1;

import java.util.ArrayList;
import java.util.List;
import dbException.*;



public class DataBase {
    
    private List<Entity> entities;
    public int idCounter;

    public DataBase() {
        entities = new ArrayList<>();
        idCounter = 1;
    }
    public void add(Entity entity) {
        
        entity.ID = idCounter;

        ++idCounter;

        entities.add(entity);
    }
    public Entity get(int id) throws EntityNotFoundException {
        for(Entity entity : entities) {
            if(entity.ID == id) {
                return entity;
            }
        }
        // if entity not found
        throw new EntityNotFoundException();
        
    }
    public void delete(int id) throws EntityNotFoundException {

        for(Entity entity : entities) {
            if(entity.ID == id) {
                entities.remove(entity);
                return;
            }
        }
        // if not
        throw new EntityNotFoundException(id);


    }
    // i dont recommend this update (your way)
    public void update(Entity e) throws EntityNotFoundException {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).ID == e.ID) {
                entities.set(i, e); 
                return; 
            }
        }

        // if not found
       throw new EntityNotFoundException();
      
        
    }


    
}
