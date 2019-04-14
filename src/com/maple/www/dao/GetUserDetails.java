package com.maple.www.dao;

import com.maple.www.Util.DbUtil;
import com.maple.www.model.User;

import java.sql.*;

import static com.maple.www.dao.CheckFormat.checkEmailFormat;
import static com.maple.www.dao.CheckFormat.checkTelFormat;

public class GetUserDetails {//用来获取数据 无法直接对数据库里的数据进行操作
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


    /*
    使用解密的方法取出用户的密码
     */
    public static String getUserPasswordT(String tel) throws Exception{
        Connection con=dbUtil.getCon();
        String sql="SELECT DECODE(password,'maple') FROM user WHERE tel=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,tel);
        ResultSet rs=pstmt.executeQuery();
        if (rs.next()){
            String password=rs.getString("DECODE(password,'maple')");
            return password;
        }else return null;
    }//通过输入手机号码获取对应的用户密码
    public static String getUserPasswordE(String email) throws Exception{
        Connection con=dbUtil.getCon();
        String sql="SELECT DECODE(password,'maple') FROM user WHERE email=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,email);
        ResultSet rs=pstmt.executeQuery();
        if (rs.next()){
            String password=rs.getString("DECODE(password,'maple')");
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
//            System.out.println("该用户名已存在");
            return 0;
        }else {
//            System.out.println("不存在该用户名，可使用");
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
            return true;
        }else {
            return false;
        }
    }//1.注册时检验手机号码是否已被注册    2.以及登陆时检验是否注册 未注册提醒用户前去注册
    public static boolean checkEmailExist(String email)throws Exception{
        Connection con=dbUtil.getCon();
        String sql="SELECT * FROM user WHERE email=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,email);
        ResultSet rs=pstmt.executeQuery();
        if (rs.next()){
            String email1Haved=rs.getString("email");
            return true;
        }else {
            return false;
        }
    }//2.注册时检验邮箱是否已被注册

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
        }else if(checkEmailFormat(str)) {
            sql = "SELECT * FROM user WHERE email=?";
        }else {
            sql = "SELECT * FROM user WHERE user_name=?";
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
            String introduction=rs.getString("introduction");
            String birthday=rs.getString("birthday");
            String sex=rs.getString("sex");
            String address=rs.getString("address");
            User user=new User(id,userName,tel,email,exp,level,power,introduction,birthday,sex,address);
            return user;
        }else return null;
    }
    /*
    1.获取用户的详细信息 用于登陆后获取用户的信息 及登录输入匹配的账号和密码 之后通过此方法获取用户的所有user数据
    2.通过输入用户名获取用户信息
     */

    public static void showFriends(User user) throws Exception {
        Connection con=dbUtil.getCon();
        String sql="SELECT friend_id FROM friend_ship WHERE user_id="+user.getId();
        Statement stmt=con.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        while (rs.next()){
            int friendId=rs.getInt("friend_id");
            sql="SELECT user_name FROM user WHERE id="+friendId;
            Statement stmt2=con.createStatement();
            ResultSet rs2=stmt2.executeQuery(sql);
            while(rs2.next()){
                String friendName=rs2.getString("user_name");
                System.out.println("用户:"+friendName);
            }
        }
        dbUtil.close(con);
    }
}
