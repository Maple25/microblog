package com.maple.www.dao;

import com.maple.www.Util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UpLevel {
    /**
     * 获取用户经验
     * 判断用户经验所处值域
     * 满足某值域则更新等级到对应等级
     */
    private static DbUtil dbUtil=new DbUtil();
    public static void upLevel(int senderId) throws Exception {
        Connection con=dbUtil.getCon();
        String sql="SELECT * FROM user WHERE id=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setInt(1,senderId);
        ResultSet rs=pstmt.executeQuery();
        if (rs.next()){
            int exp=rs.getInt("exp");
            if(exp>0&&exp<2){
                sql="UPDATE user SET level='v1' WHERE id="+senderId;
            }else if (exp>=2&&exp<5){
                sql="UPDATE user SET level='v2' WHERE id="+senderId;
            }else if (exp>=5&&exp<10){
                sql="UPDATE user SET level='v3' WHERE id="+senderId;
            }else if (exp>=10&&exp<20){
                sql="UPDATE user SET level='v4' WHERE id="+senderId;
            }else if (exp>=20){
                sql="UPDATE user SET level='v5' WHERE id="+senderId;
            }
            Statement stmt=con.createStatement();
            stmt.execute(sql);
            dbUtil.close(stmt,con);
        }
        dbUtil.close(pstmt,con);
    }
}
