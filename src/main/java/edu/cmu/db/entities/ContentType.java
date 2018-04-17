package edu.cmu.db.entities;

import javax.persistence.*;

@Entity
@Table(name = "contenttype")
@NamedQueries({
        @NamedQuery(name = "edu.cmu.db.entities.ContentType.findAll",
                query = "select ct from ContentType ct")
})

public class ContentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contentTypeID;

    @Column(name = "contentTypeName")
    private String contentType;

    public ContentType() {
    }

    public ContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getContentTypeID() {
        return contentTypeID;
    }

    public void setContentTypeID(int contentTypeID) {
        this.contentTypeID = contentTypeID;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
