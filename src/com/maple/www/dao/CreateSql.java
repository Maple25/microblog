package com.maple.www.dao;

/**
 * 这是一个用来获取查询对象对应的sql的方法
 */
public class CreateSql {

    public static String getInquireSql(String option,String str){//option:表示通过什么方式查询  str表示查询的语段
        String sql=null;
        if ("用户名".equals(option)){
            sql="SELECT * FROM user WHERE user_name LIKE %"+str+"%";//一点点模糊查询
        }else if ("".equals(option)){
            sql="";
        }

        return sql;
    }

}
