package com.king.hutoolHttp;

import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;

/**
 * hutool中定时任务
 */
public class CronTest {
    public void run(){
        System.out.println("test02 run ===========");
    }
    public static void main(String[] args){
        CronUtil.setMatchSecond(true);//开启支持秒级别的定时任务  可以再配置中配置Quartz表达式(6位)，同时兼容5位精确到分的表达式

        CronUtil.schedule("*/2 * * * * *", new Task() {
            @Override
            public void execute() {
                System.out.println("task  executed.");
            }
        });

        CronUtil.start();
    }
}
