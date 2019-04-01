package com.maple.www.dao;

import com.maple.www.Util.DbUtil;
import com.maple.www.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GetUserDetails {
    private static DbUtil dbUtil=new DbUtil();

    public static String getSenderName(int senderId) throws Exception {
        Connection con=dbUtil.getCon();
        String sql="SELECT * FROM user WHERE id=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setInt(1,senderId);
        ResultSet rs=pstmt.executeQuery();
        if (rs.next()){
            String senderName=rs.getString("user_name");
            return senderName;
        }else return null;
    }
    public static int getUserId(User user) throws Exception {//这个方法不合理 并没有设置用户名唯一
        Connection con=dbUtil.getCon();
        String sql="SELECT * FROM user WHERE user_name=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,user.getUserName());
        ResultSet rs=pstmt.executeQuery();
        if (rs.next()){
            int userId=rs.getInt("id");
            return userId;
        }else return 0;
    }

    public static int checkUserNameExist(String userName) throws Exception {
        Connection con=dbUtil.getCon();
        String sql="SELECT * FROM user WHERE user_name=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,userName);
        ResultSet rs=pstmt.executeQuery();
        if(rs.next()){
            String userNameHaved=rs.getString("user_name");
            System.out.println("该用户名已存在");
            return 0;
        }else {
            System.out.println("不存在该用户名，可使用");
            return 1;
        }
    }
}
