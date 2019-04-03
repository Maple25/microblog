package com.maple.www.service;

import com.maple.www.model.User;

import java.util.Scanner;

import static com.maple.www.dao.CheckFormat.*;
import static com.maple.www.dao.GetUserDetails.*;
import static com.maple.www.service.Register.register;

public class Login {
    public static User login() throws Exception {
        Scanner sc=new Scanner(System.in);
        String str = null;
        boolean isTelOrEmailCorrect=false;
        while(isTelOrEmailCorrect==false){//当输入的手机号码或邮箱格式或者不存在时，重复此操作
            System.out.println("请输入手机号码或邮箱");
            str=sc.nextLine();

            while (!checkTOrEFormat(str)){//检验是否为邮箱或手机号码
                str=sc.nextLine();
            }
        /*
        接下来是判断数据库里是否存在，不存在则告诉用户去注册
         */
            boolean isNeedRegister;
            String choice;
            if(checkTelFormat(str)){
                isNeedRegister=checkTelExist(str);
                isTelOrEmailCorrect=checkTelExist(str);
                if (isNeedRegister==false){
                    System.out.println("该手机号码未注册");
                    System.out.println("是否前往注册(Y/N)");
                    choice=sc.nextLine();
                    switch (choice){
                        case "Y":
                            register();
                            break;
                        default:
                            isTelOrEmailCorrect=false;
                            break;
                    }
                }
            }else {//邮箱与手机号码原理一致
                checkEmailExist(str);
                isNeedRegister=checkEmailExist(str);
                isTelOrEmailCorrect=checkEmailExist(str);
                if (isNeedRegister==false){
                    System.out.println("该邮箱未注册");
                    System.out.println("是否前往注册(Y/N)");
                    choice=sc.nextLine();
                    switch (choice){
                        case "Y":
                            register();
                            break;
                        default:
                            isTelOrEmailCorrect=false;
                            break;
                    }
                }
            }

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
