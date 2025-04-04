package example;

import db1.Entity;
import db1.Validator;
import dbException.InvalidEntityException;

public class HumanValidator implements Validator{

    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Human)) {
            throw new InvalidEntityException("Invalid entity inputed");
        }
        Human human = (Human) entity;
        if(human.age < 0 || human.name.isEmpty()) {
            throw new InvalidEntityException("Invalid human entity");
        }


    }

    
}
