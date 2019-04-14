package com.maple.www.dao;

import com.maple.www.Util.DbUtil;
import com.maple.www.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddFriend {

    private static DbUtil dbUtil=new DbUtil();
    public static void addFriendInMysql(int userId,int friendId) throws Exception {//之所以只操作friend的ID,是因为用户ID用操作时用户的ID 试试能不能
        Connection con=dbUtil.getCon();
        String sql_1="INSERT INTO friend_ship VALUES(null,?,?)";
        String sql_2="INSERT INTO friend_ship VALUES(null,?,?)";
        PreparedStatement pstmt_1=con.prepareStatement(sql_1);
        PreparedStatement pstmt_2=con.prepareStatement(sql_2);
        pstmt_1.setInt(1,userId);
        pstmt_1.setInt(2,friendId);
        pstmt_2.setInt(1,friendId);
        pstmt_2.setInt(2,userId);
        int result_1=pstmt_1.executeUpdate();
        int result_2=pstmt_2.executeUpdate();
        if(result_1==1&&result_2==1) {
            System.out.println("addFriend SUCESSFUL!");
        }else {
            System.out.println("addFriend FAILED!");
        }
    }

    public static boolean checkFriendShipExist(User user, User friend) throws Exception {
        Connection con=dbUtil.getCon();
        String sql="SELECT * FROM friend_ship WHERE user_id="+user.getId()+" AND friend_id="+friend.getId();
        Statement stmt=con.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        if(rs.next()){
            return true;
        } else return false;
    }//判断该朋友关系是否存在，存在返回true，不存在返回false
    /*
    如果输入的用户名 通过用户名获得ID 再使用addFriend;
     */
}
