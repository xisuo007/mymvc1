package com.king.dubbo;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;

/**
 * Created by ljq on 2020/5/13 11:21
 * 提供方
 */
@Activate
public class TestProviderContextFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        //处理session
        String sessionStr = RpcContext.getContext().getAttachment("session");
        //将sessionStr放入通用处理session中
        //todo 处理代码

        return invoker.invoke(invocation);
    }
}
