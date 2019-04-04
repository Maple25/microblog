package com.maple.www.model;

//enum LEVEL{v1,v2,v3,v4,v5};
public class User{
    private int id;
    private String userName;
    private String password;
    private String tel;
    private String email;
    private int exp;
    private String level;
    private boolean power;
    private int visitor;

    private String introduction;
    private String birthday;
    private String sex;
    private String address;

    public User(int id, String userName, int exp, boolean power) {//加入一般不用id 这个函数用在修改的时候
        this.id = id;
        this.userName = userName;
        this.exp = exp;
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

    public User(int id, String userName, String tel, String email, int exp, String level, boolean power) {
        this.id = id;
        this.userName = userName;
        this.tel = tel;
        this.email = email;
        this.exp = exp;
        this.level = level;
        this.power = power;
    }//登录时用的 没有密码 登陆后默认为当前用户

    public User(int id, String userName,  String tel, String email, int exp, String level, boolean power, String introduction, String birthday, String sex, String address) {
        this.id = id;
        this.userName = userName;
        this.tel = tel;
        this.email = email;
        this.exp = exp;
        this.level = level;
        this.power = power;
        this.introduction = introduction;
        this.birthday = birthday;
        this.sex = sex;
        this.address = address;
    }

    @Override
    public String toString() {
        return "用户名:"+userName+"\t手机号码:"+tel+"\t邮箱:"+email+"\t微博等级:"+level+"\t用户权限:"+power;
    }




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

    public int getVisitor() {
        return visitor;
    }

    public void setVisitor(int visitor) {
        this.visitor = visitor;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
