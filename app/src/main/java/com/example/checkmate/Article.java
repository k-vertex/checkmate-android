package com.example.checkmate;

import java.time.LocalDateTime;

public class Article {

    private int articleID;
    private int writerID;
    private String userType;
    private String title;
    private String content;
    private LocalDateTime dateTime;
    private String videoPath;
    private String writerName;

    public Article(int articleID, int writerID, String userType, String title, String content, LocalDateTime dateTime, String writerName) {
        this.articleID = articleID;
        this.writerID = writerID;
        this.userType = userType;
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
        this.writerName = writerName;
    }

    public Article(int articleID, int writerID, String userType, String title, String content, LocalDateTime dateTime, String writerName, String videoPath) {
        this(articleID, writerID, userType, title, content, dateTime, writerName);
        this.videoPath = videoPath;
    }

    public int getWriterID() {
        return writerID;
    }

    public void setWriterID(int writerID) {
        this.writerID = writerID;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public int getArticleID() {
        return articleID;
    }

    public void setArticleID(int articleID) {
        this.articleID = articleID;
    }
}
