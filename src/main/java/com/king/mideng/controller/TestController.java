package com.king.mideng.controller;

import com.king.mideng.ApiIdempotent;
import com.king.mideng.ServerResponse;
import com.king.mideng.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Resource
    private TestService testService;

    @ApiIdempotent
    @RequestMapping("testIdempotence")
    public ServerResponse testIdempotence() {
        return testService.testIdempotence();
    }
}
