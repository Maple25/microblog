package com.maple.www.dao;

import com.maple.www.Util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InquireUser {
    private static DbUtil dbUtil=new DbUtil();

    public static void inquireUser(String str) throws Exception {
        Connection con=dbUtil.getCon();
        String sql="SELECT * FROM user,microblog WHERE user.user_name=microblog.sender_name AND user.user_name='"+str+"'";//下次打开 加入查询该用户发过的微博（可根据左右连接 ）
        PreparedStatement pstmt=con.prepareStatement(sql);
        ResultSet rs=pstmt.executeQuery();
        while(rs.next()){
            int id=rs.getInt("id");
            String userName=rs.getString("user_name");
            String tel=rs.getString("tel");
            String mbText=rs.getString("mb_text");
            String senderName=rs.getString("sender_name");
            System.out.println("用户ID："+id+"\t用户名："+userName+"\t用户联系电话："+tel);
            System.out.println("发送者:"+senderName+"\t发送过的微博："+mbText+"\t测试长度："+mbText.length());
        }//想想怎么只显示一行 然后微博内容是分开的 还有分页查询

    }
    public static void inquireMicroblog(String str){

    }

    public static void main(String[] args) throws Exception {
        inquireUser("TESTMAPLE");
    }
}
