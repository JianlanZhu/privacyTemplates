package edu.cmu.db.entities;


import javax.persistence.*;
import java.sql.Blob;
import java.sql.Timestamp;

@Entity
@Table(name = "video")
@NamedQueries({
        @NamedQuery(name = "edu.cmu.db.entities.Image.findAll",
                query = "select i from Image i")
})

public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageID;

    @Column(name = "rawImage")
    private Blob rawImage;

    @Column(name = "uploadDate")
    private Timestamp uploadDate;

    @Column(name = "imageSender")
    private String imageSender;

    @Column(name = "imageSize")
    private int imageSize;

    @Column(name = "resultID")
    private int resultID;

    public Image() {
    }

    public Image(Blob rawImage, Timestamp uploadDate, String imageSender, int imageSize, int resultID) {
        this.rawImage = rawImage;
        this.uploadDate = uploadDate;
        this.imageSender = imageSender;
        this.imageSize = imageSize;
        this.resultID = resultID;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public Blob getRawImage() {
        return rawImage;
    }

    public void setRawImage(Blob rawImage) {
        this.rawImage = rawImage;
    }

    public Timestamp getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Timestamp uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getImageSender() {
        return imageSender;
    }

    public void setImageSender(String imageSender) {
        this.imageSender = imageSender;
    }

    public int getImageSize() {
        return imageSize;
    }

    public void setImageSize(int imageSize) {
        this.imageSize = imageSize;
    }

    public int getResultID() {
        return resultID;
    }

    public void setResultID(int resultID) {
        this.resultID = resultID;
    }
}
