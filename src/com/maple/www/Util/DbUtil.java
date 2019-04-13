package com.maple.www.Util;

import java.sql.*;

public class DbUtil {
    //数据库地址
    private static String dbUrl="jdbc:mysql://localhost:3306/microblog?useSSL=true&useUnicode=true&characterEncoding=utf8";//后面的?useSSL=ture是因为需要验证身份。你可以去掉试试
    //用户名
    private static String dbUserName="root";
    //密码
    private static String dbPassword="123456";
    //驱动名字
    private static String jdbcName="com.mysql.jdbc.Driver";

    public Connection getCon()throws Exception {
        Class.forName(jdbcName);
        Connection con= DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
        return con;
    }

    public void close(Statement stmt, Connection con)throws Exception{
        if(stmt!=null) {
            stmt.close();
            if(con!=null) {
                con.close();
            }
        }
//        System.out.println("数据库连接已关闭");
    }
    public void close(PreparedStatement pstmt, Connection con)throws Exception{
        if(pstmt!=null) {
            pstmt.close();
            if(con!=null) {
                con.close();
            }
        }
//        System.out.println("数据库连接已关闭");
    }
    public void close(Statement stmt)throws Exception{
        if(stmt!=null) {
            stmt.close();
        }
//        System.out.println("数据库连接已关闭");
    }
    public void close(PreparedStatement pstmt)throws Exception{
        if(pstmt!=null) {
            pstmt.close();
        }
//        System.out.println("数据库连接已关闭");
    }
    public void close(Connection con) throws SQLException {
        if (con!=null){
            con.close();
        }
    }

}
