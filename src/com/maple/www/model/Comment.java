package com.maple.www.model;

public class Comment {
    private int commentId;
    private String comment;
    private String commentator;
    private int commentOnMbId;
    private String commentTime;

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentator() {
        return commentator;
    }
    public void setCommentator(String commentator) {
        this.commentator = commentator;
    }

    public int getCommentOnMbId() {
        return commentOnMbId;
    }
    public void setCommentOnMbId(int commentOnMbId) {
        this.commentOnMbId = commentOnMbId;
    }

    public String getCommentTime() {
        return commentTime;
    }
    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public Comment(String comment, String commentator, int commentOnMbId,String commentTime) {
        this.comment = comment;
        this.commentator = commentator;
        this.commentOnMbId = commentOnMbId;
        this.commentTime = commentTime;
    }

    public Comment(String comment, String commentator, String commentTime) {//查询评论时用
        this.comment = comment;
        this.commentator = commentator;
        this.commentTime = commentTime;
    }

    @Override
    public String toString() {
        return "--"+comment+" "+commentator+" "+commentTime;
    }
}
