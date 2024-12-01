package com.example.checkmate;

import java.time.LocalDateTime;

public class Comment {

    private int commentID;
    private int articleID;
    private int writerID;
    private String writerName;
    private String content;
    private LocalDateTime dateTime;

    public Comment(int commentID, int articleID, int writerID, String writerName, String content, LocalDateTime dateTime) {
        this.commentID = commentID;
        this.articleID = articleID;
        this.writerID = writerID;
        this.writerName = writerName;
        this.content = content;
        this.dateTime = dateTime;
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public int getArticleID() {
        return articleID;
    }

    public void setArticleID(int articleID) {
        this.articleID = articleID;
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
}
