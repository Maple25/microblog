package com.maple.www.service;

import com.maple.www.Util.DbUtil;
import com.maple.www.model.Microblog;
import com.maple.www.model.User;

import static com.maple.www.service.Homepage.homepage;
import static com.maple.www.service.Login.login;
import static com.maple.www.service.OperateMicroblog.searchMicroblog;
import static com.maple.www.service.OperateMicroblog.sendMicroblog;
import static com.maple.www.service.OperateUser.inquireUser;
import static com.maple.www.service.Register.register;

public class MainProgram {

    private static DbUtil dbUtil=new DbUtil();

    public static void main(String[] args) throws Exception {
//        User user=new User("TESTMAPLE","123456789","13556412863",0,"v1",true);
//        Microblog mb=new Microblog(2,"科技","测试经验增加",0,null);
//        addUser(user);
//        modifyUserName("MAPLE","TESTMAPLE");
//        addMicroblog(mb);
//        addFriend(getUserId(user),1);
//        register();//要增加登陆时未注册的跳转
//        sendMicroblog(user);
//        searchMicroblog();
//        inquireUser();
        User user=login();//创建一个user对象来转接login登录的user;//login登录方法会返回一个User;
        homepage(user);
    }
}
