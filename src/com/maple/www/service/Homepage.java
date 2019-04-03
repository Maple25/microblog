package com.maple.www.service;

import com.maple.www.model.User;

import java.util.Scanner;

import static com.maple.www.service.Login.login;
import static com.maple.www.service.OperateMicroblog.deleteMicroblogByUser;
import static com.maple.www.service.OperateUser.inquireUser;
import static com.maple.www.service.OperateMicroblog.searchMicroblog;
import static com.maple.www.service.OperateMicroblog.sendMicroblog;

public class Homepage {
    public static void homepage(User user) throws Exception {
        Scanner sc=new Scanner(System.in);
        int choice;
        boolean isStayPage=true;
        while(isStayPage){
            System.out.println("请选择您要进行的操作\n1.发微博\n2.搜索微博\n3.搜索用户\n4.删除微博\n5.个人中心\n6.退出登录");
            choice=sc.nextInt();
            sc.nextLine();
            switch(choice){
                case 1:
                    sendMicroblog(user);
                    break;
                case 2:
                    searchMicroblog();
                    break;
                case 3:
                    inquireUser();
                    break;
                case 4:
                    deleteMicroblogByUser(user);
                    break;
                case 5:
                    break;
                case 6:
                    isStayPage=false;
                    break;
            }
        }

    }
}
