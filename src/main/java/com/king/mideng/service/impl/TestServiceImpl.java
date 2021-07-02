package com.king.mideng.service.impl;

import com.king.mideng.ServerResponse;
import com.king.mideng.service.TestService;
import org.springframework.stereotype.Service;

/**
 * Created by ljq on 2019/6/21 16:20
 */
@Service
public class TestServiceImpl implements TestService {
    @Override
    public ServerResponse testIdempotence() {
        return ServerResponse.success("testIdempotence: success");
    }

    @Override
    public ServerResponse accessLimit() {
        return ServerResponse.success("accessLimit: success");
    }
}
