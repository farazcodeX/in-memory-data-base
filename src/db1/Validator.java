package db1;

import dbException.InvalidEntityException;

public interface Validator {
    void validate(Entity entity) throws InvalidEntityException;  
}
