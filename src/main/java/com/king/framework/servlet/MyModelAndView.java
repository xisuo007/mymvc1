package com.king.framework.servlet;

import java.util.Map;

/**
 * Created by ljq on 2018/11/29 14:04
 */
public class MyModelAndView {
    //页面模板
    private String view;
    //要往页面上带过去的值
    private Map<String,Object> model;

    public MyModelAndView(String view, Map<String, Object> model) {
        this.view = view;
        this.model = model;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }
}
