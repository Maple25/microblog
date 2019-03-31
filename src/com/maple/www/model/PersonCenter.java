package com.maple.www.model;

public class PersonCenter {
    private int id;
    private String userName;
    private int mb_id;//用来对应微博的ID 不过应该可以直接用用户个人的id查询

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getMb_id() {
        return mb_id;
    }

    public void setMb_id(int mb_id) {
        this.mb_id = mb_id;
    }

    public PersonCenter(int id, String userName, int mb_id) {
        this.id = id;
        this.userName = userName;
        this.mb_id = mb_id;
    }
}
