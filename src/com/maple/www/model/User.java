package com.maple.www.model;

enum LEVEL{v1,v2,v3,v4,v5};
public class User{
    private int id;
    private String userName;
    private String password;
    private String tel;
    private String email;
    private int exp;
    private String level;
    private boolean power;

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

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public boolean isPower() {
        return power;
    }

    public void setPower(boolean power) {
        this.power = power;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public User(int id, String userName, int exp, boolean power) {//加入一般不用id 这个函数用在修改的时候
        this.id = id;
        this.userName = userName;
        this.exp = exp;
        this.power = power;
    }

    public User(String userName, int exp, boolean power) {
        this.userName = userName;
        this.exp = exp;
        this.power = power;
    }

    public User(String userName, String password, String tel, int exp, String level, boolean power) {
        this.userName = userName;
        this.password = password;
        this.tel = tel;
        this.exp = exp;
        this.level = level;
        this.power = power;
    }

    public User(String userName, String password, String tel, String email, int exp, String level, boolean power) {
        this.userName = userName;
        this.password = password;
        this.tel = tel;
        this.email = email;
        this.exp = exp;
        this.level = level;
        this.power = power;
    }

    public static void main(String[] args) {
    }
}
