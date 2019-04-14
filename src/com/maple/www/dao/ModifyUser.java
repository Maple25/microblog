package com.maple.www.dao;
/**
 *修改用户信息
 */
/*
待解决如何实现修改用户信息方法的重载
 */

import com.maple.www.Util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ModifyUser {

    private static DbUtil dbUtil=new DbUtil();

    public static void modifyUserName(String oldUserName,String newUserName) throws Exception {
        Connection con=dbUtil.getCon();
        String sql="UPDATE user SET user_name=? WHERE user_name=? ";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,newUserName);
        pstmt.setString(2,oldUserName);
        int result=pstmt.executeUpdate();
        if(result==1) {
            System.out.println("modifyUser SUCESSFUL!");
        }else {
            System.out.println("modifyUser FAILED!");
        }
        dbUtil.close(pstmt,con);
    }

    public static void main(String[] args) throws Exception {
    }
}
