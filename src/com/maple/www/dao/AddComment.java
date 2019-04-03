package com.maple.www.dao;

import com.maple.www.Util.DbUtil;
import com.maple.www.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.maple.www.dao.UpLevel.upLevel;

public class AddComment {
    private static DbUtil dbUtil=new DbUtil();
    public static boolean addComment(User user, String comment, int mbId) throws Exception {
        Connection con=dbUtil.getCon();
        String sql="INSERT INTO comment VALUES(null,?,?,?,?)";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,comment);
        pstmt.setString(2,user.getUserName());
        pstmt.setInt(3,mbId);
        SimpleDateFormat s1=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String daytime=s1.format(new Date());
        pstmt.setString(4,daytime);
        int result=pstmt.executeUpdate();
        sql="UPDATE user SET exp=exp+1 WHERE id"+user.getId();
        Statement stmt=con.createStatement();
        stmt.execute(sql);
        dbUtil.close(stmt);
        upLevel(user.getId());
        if (result==1)return true;else return false;
    }

    public static void main(String[] args) throws Exception {
//        addComment("maple","我真是太菜了",16);//淘汰
    }
}
