package db1;

import java.sql.Date;

public interface Trackable {
   void setCreationDate(Date date);
   Date getCreationDate();
   void setLastModificationDate(Date date);
   Date getLastModificationDate();    
}
