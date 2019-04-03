package com.maple.www.service;

import com.maple.www.Util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class OperateUser {
    private static DbUtil dbUtil=new DbUtil();

    public static void inquireUser() throws Exception {
        Scanner scN=new Scanner(System.in);
        System.out.println("请输入要查找的用户名：");
        String str=scN.nextLine();
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
            String mbId=rs.getString("mb_id");
            System.out.println("用户ID："+id+"\t用户名："+userName+"\t用户联系电话："+tel);
            System.out.println("发送者:"+senderName+"\t发送过的微博："+mbText);
            Connection con2=dbUtil.getCon();
            sql="SELECT*FROM comment WHERE comment_on_mb_id="+mbId;
            Statement stmt2=con2.createStatement();
            ResultSet rs2=stmt2.executeQuery(sql);
            while (rs2.next()){
                String comment=rs2.getString("comment");
                String commentTime=rs2.getString("comment_time");
                String commentator=rs2.getString("commentator");
                System.out.println("#评论#"+comment+"\t评论人: "+commentator+"\t评论时间："+commentTime);
            }
        }//想想怎么只显示一行 然后微博内容是分开的 还有分页查询

    }

    public static void inquireUserAllMb(String userName) throws Exception {
        Connection con=dbUtil.getCon();
        String sql="SELECT * FROM microblog WHERE sender_name='"+userName+"'";//下次打开 加入查询该用户发过的微博（可根据左右连接 ）
        PreparedStatement pstmt=con.prepareStatement(sql);
        ResultSet rs=pstmt.executeQuery();
        System.out.println("用户:"+userName);
        while(rs.next()){
            int mbId=rs.getInt("mb_id");
            String mbText=rs.getString("mb_text");
            String category=rs.getString("mb_category");
            int liked=rs.getInt("liked");
            String sendTime=rs.getString("mb_send_time");
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println("微博ID："+mbId+"\t赞"+liked+"\t发送时间:"+sendTime);
            System.out.println("微博正文：#"+category+"#"+mbText);
            System.out.println("----------------------------------------------------------------------------------------");
        }
    }

    public static void main(String[] args) throws Exception {

    }
}
