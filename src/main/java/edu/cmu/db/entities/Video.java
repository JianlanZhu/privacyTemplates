package edu.cmu.db.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.sql.Blob;

@Entity
@Table(name = "video")
@NamedQueries({
        @NamedQuery(name = "edu.cmu.db.entities.Video.findAll",
                query = "select v from Video v")
})

public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int videoID;

    @Column(name = "rawVideo")
    private Blob rawVideo;

    @Column(name = "uploadDate")
    private Timestamp uploadDate;

    @Column(name = "videoSender")
    private String videoSender;

    @Column(name = "videoSize")
    private String videoSize;

    @Column(name = "resultID")
    private int resultID;

    public Video() {
    }

    public Video(Blob rawVideo, Timestamp uploadDate, String videoSender, String videoSize, int resultID) {
        this.rawVideo = rawVideo;
        this.uploadDate = uploadDate;
        this.videoSender = videoSender;
        this.videoSize = videoSize;
        this.resultID = resultID;
    }

    public int getVideoID() {
        return videoID;
    }

    public void setVideoID(int videoID) {
        this.videoID = videoID;
    }

    public Blob getRawVideo() {
        return rawVideo;
    }

    public void setRawVideo(Blob rawVideo) {
        this.rawVideo = rawVideo;
    }

    public Timestamp getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Timestamp uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getVideoSender() {
        return videoSender;
    }

    public void setVideoSender(String videoSender) {
        this.videoSender = videoSender;
    }

    public String getVideoSize() {
        return videoSize;
    }

    public void setVideoSize(String videoSize) {
        this.videoSize = videoSize;
    }

    public int getResultID() {
        return resultID;
    }

    public void setResultID(int resultID) {
        this.resultID = resultID;
    }
}
