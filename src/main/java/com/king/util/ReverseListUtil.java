package com.king.util;

import com.king.controller.UserRet;
import com.king.java8.lambda.User;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by ljq on 2019/8/3 10:10
 */
public class ReverseListUtil {
    public static void main(String[] args){
        ReverseListUtil test001 = new ReverseListUtil();
        List<User> users = Arrays.asList(new User(1,"zhangsan",18,33.33),
                new User(2,"lisi",19,44.33));
        List<UserRet> userRets = test001.reverseList(users, new UserRet());
        System.out.println(userRets);

    }

    private<T,R> List<R> reverseList(List<T> list, R r){
        List<R> ret = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)) {
            return ret;
        }
        for (T t : list) {
            try {
                R o = (R)r.getClass().newInstance();
                BeanUtils.copyProperties(t,o);
                ret.add(o);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
}
