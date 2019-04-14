package com.maple.www.service;

import com.maple.www.Util.DbUtil;
import com.maple.www.model.Microblog;
import com.maple.www.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import static com.maple.www.dao.AddMicroblog.addMicroblog;
import static com.maple.www.dao.DeleteMicroblog.deleteMicroblog;
import static com.maple.www.service.OperateComment.publishComment;
import static com.maple.www.service.OperateUser.inquireUserAllMb;

public class OperateMicroblog {
    private  static DbUtil dbUtil=new DbUtil();

    public static void sendMicroblog(User user) throws Exception {
        Microblog mb=new Microblog();//构造一个空构造器就不会报错空指针了 枯辽
        Scanner sc=new Scanner(System.in);
        System.out.println("请选择微博类型:\n1.科技\n2.技术\n3.美食\n4.生活\n5.体育");
        int choice=sc.nextInt();
        sc.nextLine();
        switch (choice){
            case 1:
                mb.setMbCategory("科技");
                break;
            case 2:
                mb.setMbCategory("技术");
                break;
            case 3:
                mb.setMbCategory("美食");
                break;
            case 4:
                mb.setMbCategory("生活");
                break;
            case 5:
                mb.setMbCategory("体育");
                break;
        }
        System.out.println("请输入微博内容");
        String context=sc.nextLine();
        while(context.length()>200){
            System.out.println("超出限制长度"+(context.length()-200)+"个字\n请重新输入");
            context=sc.nextLine();
        }
        mb.setMbText(context);
        mb.setSenderId(user.getId());
        mb.setSenderName(user.getUserName());
        addMicroblog(mb);

    }

    public static String replaceSpace(String str){
        StringBuffer strTemp=new StringBuffer(str);
        String keyword=strTemp.toString().replaceAll(" ","|");
        return keyword;
    }//将输入的字符中的空格替换为|，以实现查询时的模糊查询

    public static void searchMicroblog(User user) throws Exception {//尝试实现模糊查询 正则表达式   /w+Keyword+/w
        Connection con=dbUtil.getCon();
        Scanner scK=new Scanner(System.in);
        System.out.println("请输入关键词:");
        String keyword= scK.nextLine();
        String choice;
        /*
        把Keyword里面的空格换成|
         */
        keyword=replaceSpace(keyword);
        String sql="SELECT * FROM microblog WHERE mb_text REGEXP '"+keyword+"'";
        Statement stmt=con.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        while(rs.next()){
            int senderId=rs.getInt("sender_id");
            String senderName=rs.getString("sender_name");
            String category=rs.getString("mb_category");
            String context=rs.getString("mb_text");
            int liked=rs.getInt("liked");
            String sendTime=rs.getString("mb_send_time");
            int mbId=rs.getInt("mb_id");
            System.out.println("**************************************************************************************");
            System.out.println("用户 "+senderName+":#"+category+"#"+context+"\n赞："+liked+"\t\t"+sendTime);
            Microblog mb=new Microblog(mbId,senderId,senderName,context,liked,sendTime);
            Connection con2=dbUtil.getCon();
            sql="SELECT*FROM comment WHERE comment_on_mb_id="+mbId;
            Statement stmt2=con2.createStatement();
            ResultSet rs2=stmt2.executeQuery(sql);
            while (rs2.next()){
                String comment=rs2.getString("comment");
                String commentTime=rs2.getString("comment_time");
                String commentator=rs2.getString("commentator");
                System.out.println("#评论#"+comment+"\t评论人:"+commentator+"\t评论时间："+commentTime);

            }
            System.out.println("是否评论此微博(Y/N)");
            choice= scK.nextLine();
            if (choice.equals("Y")){
                publishComment(user,mb);
            }
        }
        System.out.println("-------------------------------------------------------------------------------------");

        /*
        索引！索引弄清楚！
         */
    }
    public static void searchMicroblogWithId() throws Exception {//尝试实现模糊查询 正则表达式   /w+Keyword+/w
        Connection con=dbUtil.getCon();
        Scanner scK=new Scanner(System.in);
        System.out.println("请输入关键词:");
        String keyword= scK.nextLine();
        /*
        把Keyword里面的空格换成|
         */
        keyword=replaceSpace(keyword);
        String sql="SELECT * FROM microblog WHERE mb_text REGEXP '"+keyword+"'";
        Statement stmt=con.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        while(rs.next()){
            String senderName=rs.getString("sender_name");
            String category=rs.getString("mb_category");
            String context=rs.getString("mb_text");
            int liked=rs.getInt("liked");
            String sendTime=rs.getString("mb_send_time");
            int mbId=rs.getInt("mb_id");
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("微博ID："+mbId);
            System.out.println("用户 "+senderName+":#"+category+"#"+context+"\n赞："+liked+"\t\t"+sendTime);
            sql="SELECT*FROM comment WHERE comment_on_mb_id="+mbId;
            Statement stmt2=con.createStatement();
            ResultSet rs2=stmt2.executeQuery(sql);
            while (rs2.next()){
                String comment=rs2.getString("comment");
                String commentator=rs2.getString("commentator");
                String commentTime=rs2.getString("comment_time");
                System.out.println("#评论#"+comment+"\t评论人: "+commentator+"\t评论时间："+commentTime);
            }
        }
        System.out.println("--------------------------------------------------------------------------------------");

        /*
        索引！索引弄清楚！
         */
    }


    public static boolean checkMbIdBelong(User user,int mbId) throws Exception {
        Connection con=dbUtil.getCon();
        String sql="SELECT * FROM microblog WHERE sender_name='"+user.getUserName()+"' AND mb_id="+mbId;
        PreparedStatement pstmt=con.prepareStatement(sql);
        ResultSet rs=pstmt.executeQuery();
        if(rs.next()){
            return true;
        }else{
            System.out.println("该微博并非你所发送，你无权删除。或此微博已被删除，请重新查看已发送的微博");
            return false;
        }
    }
    public static void deleteMicroblogByUser(User user) throws Exception {
        /*
        首先先判断这个人是否为管理员
         */
        boolean power=user.isPower();
        int mbId;
        /*
        如果是管理员，选择要删除谁的微博，如果是普通用户，展示给他自己的微博 后面删除时还要增加验证他删除的是否为自己的微博
         */
        Scanner scM=new Scanner(System.in);
        if(power){
            System.out.println("请选择您要删除的微博\n1.选择用户删除\n2.通过搜索删除\n3.直接通过mb_id删除");
            int choice=scM.nextInt();
            scM.nextLine();
            switch (choice){
                case 1:
                    System.out.println("请输入要删除的微博的发送者");
                    String userNameDM=scM.nextLine();
                    inquireUserAllMb(userNameDM);
                    System.out.println("请输入要删除的微博的ID");
                    mbId=scM.nextInt();
                    scM.nextLine();
                    deleteMicroblog(mbId);
                    inquireUserAllMb(userNameDM);
                    break;
                case 2:
                    searchMicroblogWithId();
                    System.out.println("请输入要删除的微博的ID");
                    mbId=scM.nextInt();
                    scM.nextLine();
                    deleteMicroblog(mbId);
                    break;
                case 3:
                    System.out.println("请输入要删除的微博的ID");
                    mbId=scM.nextInt();
                    scM.nextLine();
                    deleteMicroblog(mbId);
            }
        }else{
            inquireUserAllMb(user.getUserName());
            System.out.println("请输入要删除的微博的ID");
            mbId=scM.nextInt();
            scM.nextLine();
            /*
            判断所输入的ID是否属于他自己的微博
             */
            if(checkMbIdBelong(user,mbId)){
                //检查此微博是否本用户所发送 决定是否有权限删除
                deleteMicroblog(mbId);
            }
        }

//        String sql="DELETE * FROM microblog WHERE mb_id="+mbId;
    }//这个有点难度 如何实现选择哪一条微博

    /*
    用于个人中心展示微博
     */
    public static void showMicroblog(User user) throws Exception {
        Connection con=dbUtil.getCon();
        String sql="SELECT * FROM microblog WHERE sender_id="+user.getId();
        Statement stmt=con.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        while(rs.next()){
            String category=rs.getString("mb_category");
            String context=rs.getString("mb_text");
            String sendTime=rs.getString("mb_send_time");
            int liked=rs.getInt("liked");
            int mbId=rs.getInt("mb_id");
            System.out.println("**************************************************************************************");
            System.out.println("#"+category+"#"+context+"\t赞："+liked+"\t评论时间："+sendTime);
            sql="SELECT*FROM comment WHERE comment_on_mb_id="+mbId;
            Statement stmt2=con.createStatement();
            ResultSet rs2=stmt2.executeQuery(sql);
            while (rs2.next()){
                System.out.println("---------------------------------------------------");
                String comment=rs2.getString("comment");
                String commentTime=rs2.getString("comment_time");
                String commentator=rs2.getString("commentator");
                System.out.println("#评论#"+comment+"\t评论人: "+commentator+"\t评论时间："+commentTime);
            }
        }

    }//会展示所有微博

    public static void main(String[] args) throws Exception {
//        User user=new User("TESTMAPLE","123456789","13556412863","zoew1942spe@live.com",0,"v1",true);
//        sendMicroblog(user);
//        searchMicroblog();
//        inquireUserAllMb("TESTMAPLE");
//        searchMicroblogWithComment();
    }
}
