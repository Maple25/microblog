package com.maple.www.dao;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.maple.www.dao.GetUserDetails.getUserPasswordE;
import static com.maple.www.dao.GetUserDetails.getUserPasswordT;

public class CheckFormat {
    public static boolean checkTelFormat(String tel){
        Pattern p=Pattern.compile("1[3-9]\\d{9}");
        Matcher m=p.matcher(tel);
        boolean b=m.matches();
        return b;
    }//检查输入是否为手机号码格式 用在注册以及其他一些区分手机号码和邮箱的时候
    public static boolean checkEmailFormat(String email){
        Pattern p=Pattern.compile("[\\w]+@[a-zA-Z0-9]+(\\.[A-Za-z0-9]{2,5}){1,3}");
        Matcher m=p.matcher(email);
        boolean b=m.matches();
        return b;
    }//检查输入是否为邮箱格式
    public static boolean checkTOrEFormat(String str){//用来检验输入的是否为手机号码或者是邮箱
        boolean isTelOrEmail;
            if (checkTelFormat(str)) {
                isTelOrEmail = true;
            } else if (checkEmailFormat(str)) {
                isTelOrEmail = true;
            } else {
                System.out.println("手机号码或邮箱格式错误，请重新输入");
                isTelOrEmail = false;
            }
        return isTelOrEmail;
    }//检查登录时输入的是否为手机号码或者时邮箱
    //他说没有更改可以提交 
}
