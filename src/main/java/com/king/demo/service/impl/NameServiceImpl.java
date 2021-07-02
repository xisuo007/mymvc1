package com.king.demo.service.impl;

import com.king.demo.service.NameService;
import com.king.framework.annotation.MYAutowired;
import com.king.framework.annotation.MYService;

/**
 * Created by ljq on 2018/11/29 14:08
 */
@MYService
public class NameServiceImpl implements NameService {
    @MYAutowired
    MYService myService;
}
