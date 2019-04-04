package com.maple.www.service;

import com.maple.www.model.User;

import java.util.Scanner;

import static com.maple.www.dao.GetUserDetails.showFriends;
import static com.maple.www.service.OperateMicroblog.showMicroblog;
import static com.maple.www.service.OperateUser.editUserData;
import static com.maple.www.service.OperateUser.inquireUserAllMb;

public class PersonCenter {
    public static void personCenter(User user) throws Exception {
        /*
        展示用户信息
         */
        System.out.print("用  户："+user.getUserName());
        if (user.isPower()==true) System.out.println("\t(管理员)");
        System.out.println("手  机："+user.getTel());
        System.out.println("邮  箱："+user.getEmail());
        System.out.println("等  级："+user.getLevel());
        System.out.println("访客量："+user.getVisitor());
        if (null!=user.getIntroduction()) System.out.println("简  介:"+user.getIntroduction());
        if (null!=user.getBirthday())System.out.println("生  日:"+user.getBirthday());
        if (!user.getSex().equals("未填写"))System.out.println("性  别:"+user.getSex());
        if (null!=user.getAddress())System.out.println("地  址:"+user.getAddress());
        Scanner sc=new Scanner(System.in);
        int choice;
        boolean isStayPersonCenter=true;
        while (isStayPersonCenter){
            System.out.println("1.查看自己发布过的内容\n2.查看我的好友\n3.编辑个人资料\n4.返回主页");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice){
                case 1:
                /*
                展示用户发过的微博
                 */
                    showMicroblog(user);
                    System.out.println();
                    break;
                case 2:
                    showFriends(user);
                    System.out.println();
                    break;
                case 3:
                    editUserData(user);
                    System.out.println();
                    break;
                case 4:
                    isStayPersonCenter=false;
                    System.out.println();
                    break;
            }

        }
        /*
        编辑个人资料是修改数据库里的数据
        新增一个修改个人数据类
         */
        /*
        设置选择，不选择回到主页或者退出登录就在个人中心循环
         */

    }
}
