package com.maple.www.service;

import com.maple.www.model.Microblog;
import com.maple.www.model.User;

import java.util.Scanner;

import static com.maple.www.dao.AddComment.addComment;

public class OperateComment {
    public static void publishComment(User user, Microblog mb) throws Exception {
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入评论内容:");
        String comment=sc.nextLine();
        while(comment.length()>50){
            System.out.println("评论不得超过50字,已超过"+(comment.length()-50)+"字");
            System.out.println("请重新输入评论");
            comment=sc.nextLine();
        }
        boolean result=addComment(user,comment,mb.getMbId());
        if(result) System.out.println("评论成功");else System.out.println("评论失败");
    }
}
