package com.king.controller;

import com.king.collection.resource.IResourceMap;
import com.king.controller.exceptionhandler.RestCtrlExceptionHandler;
import com.king.controller.validation.Create;
import com.king.controller.validation.MyValid;
import com.king.controller.validation.Update;
import com.king.controller.validation.Valid;
import com.king.util.domain.ResultDTO;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ljq on 2019/8/28 10:18
 */
@RestController
@Validated
@RequestMapping("test")
public class MyController extends RestCtrlExceptionHandler {

    @Resource
    Map<String, IResourceMap> map;


    @RequestMapping("/null")
    public void test00(){
        UserRet ret = new UserRet().setId(1);
        @NotNull(message = "年龄不能为空！") int age = ret.getAge();
        System.out.println(age);
        @NotEmpty(message = "名字不能为空！") String name = ret.getName();
        System.out.println(name);
    }

    @RequestMapping("/assert")
    public void test01() {
        String str = null;
        str = "test";
        String str0 = null;
        String str1 = "";
        Integer integer = 11;
        List<String> list = new ArrayList();
        List<String> list0 = new ArrayList();
        list.add("drff");
        list.add("1212");
        Assert.notNull(str, "字符串为空");
        Assert.notEmpty(list, "数组不能为空");

        //判断字符串是否为空
        Assert.hasText(str1, "str不能为空");
        Assert.notNull(str0, "字符串为空");
        Assert.notEmpty(list0, "数组不能为空");
    }

    @RequestMapping("/testJsonToBean")
    public String testJsonToBean( @RequestBody @Valid JsonToBean add){
        System.out.println(add.toString());
        System.out.println("添加验证测试！");
        return "通过测试";
    }

    @RequestMapping("/testAdd")
    public String test02(@Valid(groups = {Create.class}) ValueTest add){
        System.out.println("添加验证测试！");
        return "通过测试";
    }

    @RequestMapping("/testUpdate")
    @MyValid
    public String test03(@Valid(groups = {Update.class}) @RequestBody ValueTest update){
        System.out.println("修改验证测试！");
        return "修改测试通过";
    }

    @RequestMapping(value = "taskParameter",method = {RequestMethod.GET,RequestMethod.POST})
    public ResultDTO<String> taskParameter(HttpServletRequest request, JobTaskInfo info){
        ResultDTO<String> ret = new ResultDTO<>();
        if (info != null) {
            System.out.println(info.toString());
            ret.setSuccess(true);
            ret.setData(info.toString());
            return ret;
        }
        ret.setData("");
        return ret;
    }

    @RequestMapping(value = "taskParameterJson",method = {RequestMethod.GET,RequestMethod.POST})
    public ResultDTO<String> taskParameterJson(HttpServletRequest request, @RequestBody JobTaskInfo info){
        ResultDTO<String> ret = new ResultDTO<>();
        if (info != null) {
            System.out.println(info.toString());
            ret.setSuccess(true);
            ret.setData(info.toString());
            return ret;
        }
        ret.setData("");
        return ret;
    }

    @RequestMapping(value = "syn1",method = {RequestMethod.GET,RequestMethod.POST})
    public synchronized ResultDTO<String> syn1(){
        ResultDTO<String> ret = new ResultDTO<>();
        System.out.println("进入方法syn1");
        synchronized (this){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ret.setData("进入方法syn1");
            return ret;
        }
    }

    @RequestMapping(value = "syn2",method = {RequestMethod.GET,RequestMethod.POST})
    public ResultDTO<String> syn2(){
        ResultDTO<String> ret = new ResultDTO<>();
        System.out.println("调用syn2");
        ret.setData("调用syn2");
        return ret;
    }


    @RequestMapping(value = "callBack",method = {RequestMethod.GET,RequestMethod.POST})
    public void callBack(CallBackData callBackData){
        System.out.println(callBackData.toString());

    }

    @RequestMapping(value = "testRequest",method = {RequestMethod.GET,RequestMethod.POST})
    public void testRequest(HttpServletRequest request){
        try(final BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            String line = "";
            final StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null){
                sb.append(line);
            }
            System.out.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "resourceTest",method = {RequestMethod.GET,RequestMethod.POST})
    public void resourceTest(String str){
        System.out.println("进入resourceTest方法");
        for (Map.Entry<String, IResourceMap> entry : map.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue().get(str));
        }
    }
}
