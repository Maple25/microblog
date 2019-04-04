package com.maple.www.dao;

import com.maple.www.Util.DbUtil;
import com.maple.www.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddUser {

    private static DbUtil dbUtil=new DbUtil();

    public static void addUser(User user) throws Exception {
        Connection con=dbUtil.getCon();
        String sql="INSERT INTO user VALUES(null,?,ENCODE(?,'maple'),?,?,?,?,?,null,null,null,null,null)";//写一个获得sql的方法，根据输入的类型生成对应的sql语句  //使用加密方法将密码存储进数据库
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,user.getUserName());
        pstmt.setString(2,user.getPassword());
        pstmt.setString(3,user.getTel());
        pstmt.setString(4,user.getEmail());
        pstmt.setInt(5,user.getExp());
        pstmt.setString(6,user.getLevel());
        pstmt.setBoolean(7,user.isPower());
        int result=pstmt.executeUpdate();
        if(result==1) {
            System.out.println("注册成功!");
        }else {
            System.out.println("注册失败!");
        }
        dbUtil.close(pstmt,con);
    }

    public static void main(String[] args) throws Exception {
        User user=new User("testadduser","123456abc","13556412831","zoew1942spe1@live.com",0,"v1",false);
        addUser(user);
    }
}
