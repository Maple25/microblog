package com.maple.www.service;

import com.maple.www.Util.DbUtil;
import com.maple.www.model.Comment;
import com.maple.www.model.Microblog;
import com.maple.www.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import static com.maple.www.dao.AddFriend.addFriendInMysql;
import static com.maple.www.dao.AddFriend.checkFriendShipExist;
import static com.maple.www.dao.CheckFormat.checkTelFormat;
import static com.maple.www.dao.GetUserDetails.checkTelExist;
import static com.maple.www.dao.GetUserDetails.getUserData;
import static com.maple.www.dao.GetUserDetails.getUserId;

public class OperateUser {
    private static DbUtil dbUtil=new DbUtil();
    private static final int N=5,M=10;

    public static void inquireUser() throws Exception {
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入要查找的用户名");
        String userName=sc.nextLine();
        Connection con=dbUtil.getCon();
        String sql="SELECT * FROM user WHERE user_name='"+userName+"'";
        Statement stmt=con.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        while(rs.next()){
            int id=rs.getInt("id");
            sql="UPDATE user SET visitor=visitor+1 WHERE id="+id;//经测试 会执行
            Statement stmt2=con.createStatement();
            stmt2.execute(sql);//使访客数量++

            String userName2=rs.getString("user_name");
            String tel=rs.getString("tel");
            String email=rs.getString("email");
            String introduction=rs.getString("introduction");
            String birthday=rs.getString("birthday");
            String sex=rs.getString("sex");
            String address=rs.getString("address");

            System.out.println("用户名:"+userName2);
            System.out.println("手  机:"+tel);
            System.out.println("邮  箱:"+email);
            if (null!=introduction) System.out.println("简  介:"+introduction);
            if (null!=birthday)System.out.println("生  日:"+birthday);
            if (!sex.equals("未填写"))System.out.println("性  别:"+sex);
            if (null!=address)System.out.println("地  址:"+address);

            sql="SELECT * FROM microblog WHERE sender_id="+id+" ORDER BY mb_send_time DESC";
            Statement stmt3=con.createStatement();
            ResultSet rs3=stmt3.executeQuery(sql);
            Microblog[] mb=new Microblog[N];//只显示最新的五条数据
            Comment[][] cm=new Comment[N][M];//暂时显示十条评论
            int i;int j;
            for (i=0;i<N&&rs3.next();i++){
                String category=rs3.getString("mb_category");
                String context=rs3.getString("mb_text");
                String sendTime=rs3.getString("mb_send_time");
                int mbId=rs3.getInt("mb_id");
                int liked=rs3.getInt("liked");
//                System.out.println("************************************************************************************");
//                System.out.println("************************************************************************************");
//                System.out.println("#"+category+"#"+context+"\t赞："+liked+"\t评论时间："+sendTime);
                mb[i]=new Microblog(mbId,userName,category,context,liked,sendTime);
                sql="SELECT*FROM comment WHERE comment_on_mb_id="+mbId;
                Statement stmt4=con.createStatement();
                ResultSet rs4=stmt4.executeQuery(sql);
                for (j=0;j<M&&rs4.next();j++){
                    String commentTime=rs4.getString("comment_time");
                    String comment=rs4.getString("comment");
                    String commentator=rs4.getString("commentator");
//                    System.out.println("---------------------------------------------------");
//                    System.out.println("#评论#"+comment+"\t评论人: "+commentator+"\t评论时间："+commentTime);
                    cm[i][j]=new Comment(comment,commentator,commentTime);
                }
            }
            System.out.println("************************************************************************************");
            System.out.println(mb[0]);
            for(j=0;j<M;j++){
                if (null!=cm[0][j]){
                    System.out.println("---------------------------------------------------");
                    System.out.println(cm[0][j]);
                }
            }
            System.out.println("是否查看下一页（Y/N）");
            String choice;
            i=0;
            choice=sc.nextLine();
            while (choice.equals("Y")){
                i++;
                System.out.println("************************************************************************************");
                System.out.println(mb[i]);
                for(j=0;j<M;j++){
                    if (null!=cm[i][j]){
                        System.out.println("---------------------------------------------------");
                        System.out.println(cm[i][j]);
                    }
                }
                if (i<N-1){
                    System.out.println("是否查看下一页（Y/N）");
                    choice=sc.nextLine();
                }else{
                    System.out.println("最多能查看该用户最新5条微博");
                    choice="N";
                }
            }

        }
    }//查询用户个人资料

    public static void inquireUser2() throws Exception {
        Scanner scN=new Scanner(System.in);
        System.out.println("请输入要查找的用户名：");
        String str=scN.nextLine();
        Connection con=dbUtil.getCon();
        String sql="SELECT * FROM user,microblog WHERE user.user_name=microblog.sender_name AND user.user_name='"+str+"'";
        PreparedStatement pstmt=con.prepareStatement(sql);
        ResultSet rs=pstmt.executeQuery();
        while(rs.next()){
            int id=rs.getInt("id");
            /*
            增加用户访客量;
             */
            sql="UPDATE user SET visitor=visitor+1 WHERE id="+id;
            Statement stmt3=con.createStatement();
            stmt3.execute(sql);

            String userName=rs.getString("user_name");
            String tel=rs.getString("tel");
            String mbText=rs.getString("mb_text");
            String senderName=rs.getString("sender_name");
            String mbId=rs.getString("mb_id");
            System.out.println("用户ID："+id+"\t用户名："+userName);
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
    }/*旧版本 已被替换*/

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

    public static void addFriend(User user) throws Exception {
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入要添加的用户名：");
        String friendName=sc.nextLine();
        User friend=getUserData(friendName);
        if (checkFriendShipExist(user,friend)){
            System.out.println("你们已成为好友，不可重复添加");
        }else{
            addFriendInMysql(user.getId(),friend.getId());
        }

    }

    public static void editUserData(User user) throws Exception {
        Scanner sc=new Scanner(System.in);
        String sql=null;
        System.out.println("请选择要更改的信息:\n1.用户密码\n2.手机号码\n3.邮箱\n4.简介\n5.生日\n6.性别\n7.地址");
        int choice=sc.nextInt();sc.nextLine();
        switch (choice){
            case 1:
                System.out.println("请输入密码");
                String password=sc.nextLine();
                System.out.println("请确认密码");
                String password_confirm=sc.nextLine();
                while(!password.equals(password_confirm)){
                    System.out.println("两次密码不一致，请重新输入密码");
                    System.out.println("请输入密码:");
                    password=sc.nextLine();
                    System.out.println("请确认密码:");
                    password_confirm=sc.nextLine();
                }
                sql="UPDATE user SET password=ENCODE('"+password+"','maple') WHERE id="+user.getId();
                break;
            case 2:
                System.out.println("请输入新的手机号码");
                String newTel=sc.nextLine();
                boolean checkTel=false;
                while(checkTel==false){
                    while(!checkTelFormat(newTel)){
                        System.out.println("您输入的手机号码格式错误,请重新输入手机号码");
                        newTel=sc.nextLine();
                    }
                    if(checkTelExist(newTel)){
                        System.out.println("该手机号码已被注册，请重新输入手机号码");
                        newTel=sc.nextLine();
                    }else checkTel=true;
                }
                sql="UPDATE user SET tel='"+newTel+"' WHERE id="+user.getId();
                break;
            case 3:
                System.out.println("请输入新的手机号码");
                String newEmail=sc.nextLine();
                boolean checkEmail=false;
                while(checkEmail==false){
                    while(!checkTelFormat(newEmail)){
                        System.out.println("您输入的邮箱格式错误,请重新输入邮箱");
                        newEmail=sc.nextLine();
                    }
                    if(checkTelExist(newEmail)){
                        System.out.println("该邮箱已被注册，请重新输入邮箱");
                        newEmail=sc.nextLine();
                    }else checkEmail=true;
                }
                sql="UPDATE user SET email='"+newEmail+"' WHERE id="+user.getId();
                break;
            case 4:
                System.out.println("请输入自我介绍");
                user.setIntroduction(sc.nextLine());
                sql="UPDATE user SET introduction='"+user.getIntroduction()+"' WHERE id="+user.getId();
                break;
            case 5:
                System.out.println("请输入您的出生年");
                String year=sc.nextLine();
                System.out.println("请输入您的出生月");
                String month=sc.nextLine();
                System.out.println("请输入您的出生日");

                String day=sc.nextLine();
                user.setBirthday(year+"-"+month+"-"+day);
                sql="UPDATE user SET birthday='"+user.getBirthday()+"' WHERE id="+user.getId();
                break;
            case 6:
                System.out.println("请选择您的性别\n1.男\n2.女");
                int sexChoice=sc.nextInt();sc.nextLine();
                switch (sexChoice){
                    case 1:
                        user.setSex("男");
                        break;
                    case 2:
                        user.setSex("女");
                        break;
                    default:
                        user.setSex("未填写");
                        break;
                }
                sql="UPDATE user SET sex='"+user.getSex()+"' WHERE id="+user.getId();
                break;
            case 7:
                System.out.println("请输入您的住址");
                user.setAddress(sc.nextLine());
                sql="UPDATE user SET address='"+user.getAddress()+"' WHERE id="+user.getId();
                break;
            default:
                break;
        }
        Connection con=dbUtil.getCon();
        Statement stmt=con.createStatement();
        stmt.execute(sql);
        dbUtil.close(stmt,con);

    }


    public static void main(String[] args) throws Exception {
        inquireUser();
    }
}
