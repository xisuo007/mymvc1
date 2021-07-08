package com.king.thread.completablefuture;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.concurrent.CompletableFuture;

/**
 * Created by ljq on 2021-7-7 17:27
 */
@RestController
@RequestMapping("testCompletableFuture")
public class CompletableFutureController {

    @PostMapping("redirect")
    public CompletableFuture<ModelAndView> redirect(){
        return CompletableFuture.supplyAsync(()->{
            this.delay();
            final RedirectView redirectView = new RedirectView("https://www.baidu.com");
            redirectView.addStaticAttribute("hint","组装返回数据");
            return new ModelAndView(redirectView);
        });
    }

    @PostMapping("async")
    public CompletableFuture<String> async(){
        System.out.println("async method start");
        return CompletableFuture.supplyAsync(()->{
            this.delay();
            return "异步返回结果";
        }).whenComplete((res,ex)->{
            System.out.println("async method completely,res = "+ res+", ex = "+ex);
        });
    }

    protected void delay(){
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
