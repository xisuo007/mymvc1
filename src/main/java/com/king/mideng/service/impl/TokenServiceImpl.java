package com.king.mideng.service.impl;

import cn.hutool.core.text.StrBuilder;
import com.king.mideng.*;
import com.king.mideng.service.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Created by ljq on 2019/6/21 14:59
 */
@Service
public class TokenServiceImpl implements TokenService {
    private static final String TOKEN_NAME = "token";

    @Resource
    private JedisUtil jedisUtil;

    @Override
    public ServerResponse createToken() {
        String str = UUID.randomUUID().toString().replaceAll("_","");
        StrBuilder token = new StrBuilder();
        token.append(Constant.Redis.TOKEN_PREFIX).append(str);
        jedisUtil.set(token.toString(),token.toString(),Constant.Redis.EXPIRE_TIME_HOUR);
        return ServerResponse.success(token.toString());
    }

    @Override
    public void checkToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_NAME);
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter(TOKEN_NAME);
            if (StringUtils.isEmpty(token)) {
                throw new ServiceException(ResponseCode.ILLEGAL_ARGUMENT.getMsg());
            }
        }
        if (!jedisUtil.exists(token)) {
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getCode()+"",ResponseCode.REPETITIVE_OPERATION.getMsg());
        }

        Long del = jedisUtil.del(token);
        if(del <= 0){
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getCode()+"",ResponseCode.REPETITIVE_OPERATION.getMsg());
        }
    }
}
