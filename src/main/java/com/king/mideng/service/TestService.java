package com.king.mideng.service;

import com.king.mideng.ServerResponse;

/**
 * Created by ljq on 2019/6/21 16:19
 */
public interface TestService {
    ServerResponse testIdempotence();
    ServerResponse accessLimit();
}
