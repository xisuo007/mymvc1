package com.king.miaosha.service;

import com.king.miaosha.interceptor.CacheLock;
import com.king.miaosha.interceptor.LockedObject;

/**
 * Created by ljq on 2019/6/25 11:53
 */
public interface TestKill {
    @CacheLock(lockedPrefix = "TEST_PREFIX")
    void secKill(String arg1, @LockedObject Long arg2);
}
