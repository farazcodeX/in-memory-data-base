package example;

import java.sql.Date;

import db1.Entity;
import db1.Trackable;

public class Document extends Entity implements Trackable{

    private Date creationDate;
    private Date lastModificationDate;
    public static final int DOCUMENT_ENTITY_CODE = 69;
    String content;

    public Document(String content) {
        this.content = content;
    }

    @Override
    public Entity copy() {
        Document docCopy = new Document(content);
        docCopy.id = id;

        return docCopy;
    }

    @Override
    public int getEntityCode() {
        return DOCUMENT_ENTITY_CODE;  
    }

    @Override
    public void setCreationDate(Date date) {
        creationDate = date;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setLastModificationDate(Date date) {
        lastModificationDate = date;
    }

    @Override
    public Date getLastModificationDate() {
        return lastModificationDate;
    }
    
}
