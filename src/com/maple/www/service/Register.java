package com.maple.www.service;

import com.maple.www.Util.DbUtil;
import com.maple.www.model.User;

import java.sql.Connection;
import java.sql.SQLOutput;
import java.util.Scanner;


import static com.maple.www.dao.AddUser.addUser;
import static com.maple.www.dao.CheckFormat.checkEmailFormat;
import static com.maple.www.dao.CheckFormat.checkTelFormat;
import static com.maple.www.dao.GetUserDetails.checkEmailExist;
import static com.maple.www.dao.GetUserDetails.checkTelExist;
import static com.maple.www.dao.GetUserDetails.checkUserNameExist;

public class Register {

    private static DbUtil dbUtil=new DbUtil();
    public static void register() throws Exception {
        Scanner sc=new Scanner(System.in);
        System.out.println("请选择注册用户类型");
        System.out.println("1.普通用户\n2.管理员");
        int choice=sc.nextInt();
        sc.nextLine();//这个问题不是特别懂 关于nextInt后面nextLine的问题https://blog.csdn.net/sun8112133/article/details/84350216   https://blog.csdn.net/sinat_38301574/article/details/79588366
        boolean power=false;
        switch(choice){
            case 1:
                power=false;
                break;
            case 2:
                System.out.println("请输入管理权限密码");
                String adminpassword;
                while(power==false){
                    adminpassword=sc.nextLine();
                    if(!adminpassword.equals("admin123")){
                        System.out.println("管理员权限密码错误，请重试");
                    }else power=true;
                }
                break;
        }
        System.out.println("请输入用户名");
        String userName=sc.nextLine();
        while(checkUserNameExist(userName) == 0){
            System.out.println("该用户名已被使用");
            System.out.println("请重新输入用户名");
            userName=sc.nextLine();
        }
        System.out.println("请输入密码");
        String password=sc.nextLine();
        System.out.println("请确认密码");
        String password_confirm=sc.nextLine();
        while(!password.equals(password_confirm)){
            System.out.println("两次密码不一致，请重新输入密码");
            System.out.println("请输入密码");
            password=sc.nextLine();
            System.out.println("请确认密码");
            password_confirm=sc.nextLine();
        }
        System.out.println("请输入手机号码");
        String tel=sc.nextLine();
        boolean checkTel=false;
        while(checkTel==false){
            while(!checkTelFormat(tel)){
                System.out.println("您输入的手机号码格式错误,请重新输入");
                tel=sc.nextLine();
            }
            if(checkTelExist(tel)){
                System.out.println("该手机号码已被注册，请重新输入手机号码");
                tel=sc.nextLine();
            }else checkTel=true;
        }

        System.out.println("请输入邮箱");
        String email=sc.nextLine();//以及实现利用email或者tel以及用户名登录
        boolean checkEmail=false;
        while (checkEmail==false){
            while(checkEmailFormat(email)==false){
                System.out.println("您输入的邮箱地址格式错误,请重新输入");
                email=sc.nextLine();
            }
            if (checkEmailExist(email)){
                System.out.println("该邮箱已被注册，请重新输入邮箱");
                email=sc.nextLine();
            }else checkEmail=true;
        }
        User user=new User(userName,password,tel,email,0,"v1",power);
        user.setSex("未填写");
        addUser(user);
    }

    public static void main(String[] args) throws Exception {
        register();
    }

}
