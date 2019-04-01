package com.maple.www.service;

import com.maple.www.model.User;

import java.util.Scanner;

import static com.maple.www.dao.CheckFormat.*;
import static com.maple.www.dao.GetUserDetails.*;

public class Login {
    public static User login() throws Exception {
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入手机号码或邮箱");
        String str = null;
        str=sc.nextLine();
        /*
        将接下来的方法封装起来
         */
        while (!checkTOrEFormat(str)){
            str=sc.nextLine();
        }
        /*
        接下来是判断数据库里是否存在，不存在则告诉用户去注册
         */
        if(checkTelFormat(str)){
            checkTelExist(str);
        }else {
            checkEmailExist(str);
        }
        System.out.println("请输入密码");
        String password=sc.nextLine();
        /*
        接下来要验证密码与账号是否匹配
         */
        while(!checkPassword(password,str)){
            System.out.println("密码错误，请重新输入");
            password=sc.nextLine();
        }
        User user=getUserData(str);
        return user;
    }
}
