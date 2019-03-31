package com.maple.www.service;

import com.maple.www.Util.DbUtil;
import com.maple.www.model.User;

import java.sql.Connection;
import java.sql.SQLOutput;
import java.util.Scanner;


import static com.maple.www.dao.AddUser.addUser;
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
        while(tel.length()!=11){
            System.out.println("您输入的手机号码格式错误,请重新输入");
            tel=sc.nextLine();
        }
        System.out.println("请输入邮箱");
        String email=null;
        email=sc.nextLine();//待后期解决如何判断email的格式验证以及在user里面增加email字段，以及实现利用email或者tel以及用户名登录
        User user=new User(userName,password,tel,email,0,"v1",power);
        addUser(user);

    }
}
