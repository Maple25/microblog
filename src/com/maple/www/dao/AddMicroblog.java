package com.maple.www.dao;

import com.maple.www.Util.DbUtil;
import com.maple.www.model.Microblog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.maple.www.dao.GetUserDetails.getSenderName;
import static com.maple.www.dao.UpLevel.upLevel;

public class AddMicroblog {

    private static DbUtil dbUtil=new DbUtil();

    public static void addMicroblog(Microblog mb) throws Exception {
        Connection con=dbUtil.getCon();
        String sql="INSERT INTO microblog VALUES(null,?,?,?,?,?,?)";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setInt(1,mb.getSenderId());//后面要怎么修改成自动获取用户的ID
        pstmt.setString(2,mb.getSenderName());//再建一个类 通过发送者ID获取发送者用户名-----
        pstmt.setString(3,mb.getMbCategory());//3-30再建一个类getMicroblogCategory 获得类别 等能搞图形化再看看这部分怎么搞 //之前不成功是因为“后台代码通过jdbc写sql将数据传入数据库”没设置 参考https://blog.csdn.net/lsr40/article/details/78736855
        pstmt.setString(4,mb.getMbText());
        pstmt.setInt(5,mb.getLiked());
        SimpleDateFormat s1=new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String daytime=s1.format(new Date());
        pstmt.setString(6,daytime);//微博发送时间
        int result=pstmt.executeUpdate();
        if(result==1) {
            System.out.println("addMicroblog SUCESSFUL!");
        }else {
            System.out.println("addMicroblog FAILED!");
        }
        dbUtil.close(pstmt);
        sql="UPDATE user SET exp=exp+1 WHERE id="+mb.getSenderId();
        Statement stmt=con.createStatement();
        stmt.execute(sql);
        dbUtil.close(stmt);
        upLevel(mb.getSenderId());
    }
}
