package com.maple.www.service;

import com.maple.www.model.Microblog;
import com.maple.www.model.User;

import java.util.Scanner;

import static com.maple.www.dao.AddMicroblog.addMicroblog;

public class OperateMicroblog {
    public static void sendMicroblog(User user) throws Exception {
        Microblog mb=new Microblog();//构造一个空构造器就不会报错空指针了 枯辽
        Scanner sc=new Scanner(System.in);
        System.out.println("请选择微博类型");
        int choice=sc.nextInt();
        sc.nextLine();
        switch (choice){
            case 1:
                mb.setMbCategory("科技");
                break;
            case 2:
                mb.setMbCategory("科技");
                break;
            case 3:
                mb.setMbCategory("美食");
                break;
            case 4:
                mb.setMbCategory("生活");
                break;
            case 5:
                mb.setMbCategory("体育");
                break;
        }
        System.out.println("请输入微博内容");
        String context=sc.nextLine();
        while(context.length()>200){
            System.out.println("超出限制长度"+(context.length()-200)+"个字\n请重新输入");
            context=sc.nextLine();
        }
        mb.setMbText(context);
        mb.setSenderId(user.getId());
        mb.setSenderName(user.getUserName());
        addMicroblog(mb);

    }

    public static void deleteMicroblog(){

    }

    public static void main(String[] args) throws Exception {
        User user=new User("TESTMAPLE","123456789","13556412863","zoew1942spe@live.com",0,"v1",true);
        sendMicroblog(user);
    }
}
