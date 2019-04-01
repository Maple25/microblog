package com.maple.www.dao;

import com.maple.www.Util.DbUtil;
import com.maple.www.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.maple.www.dao.CheckFormat.checkTelFormat;

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
    /*
    已弃用：根据发微博人的ID获取他的名字  后期登陆后能获取该用户名 无需使用该方法，可考虑优化删掉
     */



    public static int getUserId(User user) throws Exception {
        Connection con=dbUtil.getCon();
        String sql="SELECT * FROM user WHERE user_name=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,user.getUserName());
        ResultSet rs=pstmt.executeQuery();
        if (rs.next()){
            int userId=rs.getInt("id");
            return userId;
        }else return 0;
    }//这个方法不合理 并没有设置用户名唯一

    public static String getUserPasswordT(String tel) throws Exception{
        Connection con=dbUtil.getCon();
        String sql="SELECT * FROM user WHERE tel=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,tel);
        ResultSet rs=pstmt.executeQuery();
        if (rs.next()){
            String password=rs.getString("password");
            return password;
        }else return null;
    }//通过输入手机号码获取对应的用户密码
    public static String getUserPasswordE(String email) throws Exception{
        Connection con=dbUtil.getCon();
        String sql="SELECT * FROM user WHERE email=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,email);
        ResultSet rs=pstmt.executeQuery();
        if (rs.next()){
            String password=rs.getString("password");
            return password;
        }else return null;
    }//通过输入邮箱获取对应的用户密码

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
    }//注册时检验用户名是否存在
    public static boolean checkTelExist(String tel)throws Exception{
        Connection con=dbUtil.getCon();
        String sql="SELECT * FROM user WHERE tel=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,tel);
        ResultSet rs=pstmt.executeQuery();
        if (rs.next()){
            String telHaved=rs.getString("tel");
            System.out.println(telHaved+"该号码已存在，可以用来登录");
            return true;
        }else {
            System.out.println(tel+"该号码未注册，请先前往注册");
            return false;
        }
    }//注册时检验手机号码是否已被注册  以及登陆时检验是否注册 未注册提醒用户前去注册
    public static boolean checkEmailExist(String email)throws Exception{
        Connection con=dbUtil.getCon();
        String sql="SELECT * FROM user WHERE email=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,email);
        ResultSet rs=pstmt.executeQuery();
        if (rs.next()){
            String email1Haved=rs.getString("email");
            System.out.println(email1Haved+"该邮箱已存在，可以用来登录");
            return true;
        }else {
            System.out.println(email+"该邮箱未注册，请先前往注册");
            return false;
        }
    }//注册时检验邮箱是否已被注册

    public static boolean checkPassword(String password,String str) throws Exception {
        boolean isPassword=false;
        if(checkTelFormat(str)){
            if (password.equals(getUserPasswordT(str))){
                isPassword=true;
            }
        }else {
            if (password.equals(getUserPasswordE(str))){
                isPassword=true;
            }
        }
        return isPassword;
    }//检验输入的密码与手机号码邮箱或对应的密码是否相同; 用在登陆时

    public static User getUserData(String str) throws Exception {
        Connection con=dbUtil.getCon();
        String sql=null;
        if (checkTelFormat(str)){
            sql="SELECT * FROM user WHERE tel=?";
        }else {
            sql = "SELECT * FROM user WHERE email=?";
        }
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,str);
        ResultSet rs=pstmt.executeQuery();
        if (rs.next()){
            int id=rs.getInt("id");
            String userName=rs.getString("user_name");
            String tel=rs.getString("tel");
            String email=rs.getString("email");
            int exp=rs.getInt("exp");
            String level=rs.getString("level");
            boolean power=rs.getBoolean("power");
            User user=new User(id,userName,userName,email,exp,level,power);
            return user;
        }else return null;
    }//获取用户的详细信息 用于登陆后获取用户的信息
}
