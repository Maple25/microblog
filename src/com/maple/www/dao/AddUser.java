package com.maple.www.dao;

import com.maple.www.Util.DbUtil;
import com.maple.www.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddUser {

    private static DbUtil dbUtil=new DbUtil();

    public static void addUser(User user) throws Exception {
        Connection con=dbUtil.getCon();
        String sql="INSERT INTO user VALUES(null,?,?,?,?,?,?,?)";//写一个获得sql的方法，根据输入的类型生成对应的sql语句
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
            System.out.println("addUser SUCESSFUL!");
        }else {
            System.out.println("addUser FAILED!");
        }
        dbUtil.close(pstmt,con);
    }
}
