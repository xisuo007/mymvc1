package com.king.dubbo;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;

import java.util.HashMap;

/**
 * Created by ljq on 2020/5/13 11:07
 * dubbo隐式传参，rpccontext相当于dubbo的上下文
 * 消费方
 */
@Activate
public class TestConsumerContextFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        HashMap<String, String> context = new HashMap<>();
        //处理session
        String sessionStr = "{\"username\":\"testUser\"}";
        context.put("session",sessionStr);
        RpcContext.getContext().setAttachments(context);
        return invoker.invoke(invocation);
    }
}
