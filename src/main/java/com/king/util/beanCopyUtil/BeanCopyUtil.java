package com.king.util.beanCopyUtil;

import com.king.controller.UserRet;
import com.king.java8.lambda.User;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by ljq on 2020/1/14 17:01
 */
public class BeanCopyUtil extends BeanUtils {

    /**
     * 集合数据拷贝
     * @param sources 数据源
     * @param target 目标类
     */
    public static<S,T> List<T> copyListProperties(List<S> sources, Supplier<T> target){
        return copyListProperties(sources,target,null);
    }

    public static <S,T> List<T> copyListProperties(List<S> sources, Supplier<T> target, BeanCopyUtilCallBack<S,T> callBack){
        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T t = target.get();
            copyProperties(source,t);
            list.add(t);
            if (callBack != null) {
                //回调
                callBack.callBack(source,t);
            }
        }
        return list;
    }

    public static void main(String[] args){
        List<User> users = Arrays.asList(new User().setAge(18).setName("张三").setPrice(12.3).setId(1), new User().setAge(19).setName("张三2").setPrice(12.3).setId(2));
        List<UserRet> userRets = BeanCopyUtil.copyListProperties(users, UserRet::new);
        System.out.println("userslist:=="+userRets);

        List<UserRet> test = BeanCopyUtil.copyListProperties(users, UserRet::new, (user, userRet) -> {
            userRet.setHRSB("test");
        });
        System.out.println("usersList指定字段值:=="+test);
    }
}
