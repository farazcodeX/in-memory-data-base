import db1.Database;
import dbException.EntityNotFoundException;
import example.Human;
import example.HumanValidator;

public class Main {
    public static void main(String[] args) {
               Database.registerValidator(Human.HUMAN_ENTITY_CODE, new HumanValidator());

        Human ali = new Human("Ali", -10);
        Database.add(ali);
    }
}