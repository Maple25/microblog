package com.maple.www.model;

enum cg{科技,生活,技术};//微博类别待做 可以写一个方法来输入 就像create sql
public class Microblog {
    private int senderId;
    private String mbCategory;
    private String mbText;
    private int liked;
    private String mbSendTime;
    private int mbId;//微博的编号 当主键使用

    public int getSenderId() {
        return senderId;
    }
    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getMbCategory() {
        return mbCategory;
    }
    public void setMbCategory(String mbCategory) {
        this.mbCategory = mbCategory;
    }

    public String getMbText() {
        return mbText;
    }
    public void setMbText(String mbText) {
        this.mbText = mbText;
    }

    public int getMbId() {
        return mbId;
    }
    public void setMbId(int mbId) {
        this.mbId = mbId;
    }

    public int getLiked() {
        return liked;
    }
    public void setLiked(int liked) {
        this.liked = liked;
    }

    public String getMbSendTime() {
        return mbSendTime;
    }
    public void setMbSendTime(String mbSendTime) {
        this.mbSendTime = mbSendTime;
    }

    public Microblog(int senderId, String mbCategory, String mbText, String mbSendTime) {
        this.senderId = senderId;
        this.mbCategory = mbCategory;
        this.mbText = mbText;
        this.mbSendTime = mbSendTime;
    }

    public Microblog(int senderId, String mbCategory, String mbText, int liked, String mbSendTime) {
        this.senderId = senderId;
        this.mbCategory = mbCategory;
        this.mbText = mbText;
        this.liked = liked;
        this.mbSendTime = mbSendTime;
    }

    @Override
    public String toString() {
        return senderId+" "+mbCategory+" "+mbText+" "+mbSendTime;
    }

    public static void main(String[] args) {
        User user =new User("maple",0,true);
        Microblog mb=new Microblog(1,"科技","微博正文","NOW");
        System.out.println(mb);
    }

}
