package com.maple.www.dao;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckFormat {
    public static boolean checkTelFormat(String tel){
        Pattern p=Pattern.compile("1[3-9]\\d{9}");
        Matcher m=p.matcher(tel);
        boolean b=m.matches();
        return b;
    }
    public static boolean checkEmailFormat(String email){
        Pattern p=Pattern.compile("[\\w]+@[a-zA-Z0-9]+(\\.[A-Za-z0-9]{2,5}){1,3}");
        Matcher m=p.matcher(email);
        boolean b=m.matches();
        return b;
    }

}
