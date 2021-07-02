package com.king.mideng.service;


import com.king.mideng.ServerResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ljq on 2019/6/21 14:47
 */
public interface TokenService {
    ServerResponse createToken();
    void checkToken(HttpServletRequest request);

}
