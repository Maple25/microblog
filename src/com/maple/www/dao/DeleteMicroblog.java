package com.maple.www.dao;

import com.maple.www.Util.DbUtil;

import java.sql.Connection;
import java.sql.Statement;

public class DeleteMicroblog {
    private static DbUtil dbUtil=new DbUtil();

    public static void deleteMicroblog(int mdId) throws Exception {
        Connection con=dbUtil.getCon();
        String sql="DELETE FROM microblog WHERE mb_id="+mdId;
        Statement stmt=con.createStatement();
        int result=stmt.executeUpdate(sql);
        if (result==1){
            System.out.println("删除成功！");
        }else{
            System.out.println("删除失败！");
        }
        dbUtil.close(stmt,con);
    }

}
