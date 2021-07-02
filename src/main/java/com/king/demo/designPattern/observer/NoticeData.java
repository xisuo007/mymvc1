package com.king.demo.designPattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljq on 2019/3/22 16:53
 */
public class NoticeData implements Admin {
    private List<Server> servers;
    private String msg;

    public NoticeData() {
        servers = new ArrayList<>();
    }

    public void setMsg(String msg){
        this.msg = msg;
        tell();
    }

    @Override
    public void add(Server s) {
        servers.add(s);
    }

    @Override
    public void del(Server s) {
        if (servers.indexOf(s) >=0) {
            servers.remove(s);
        }
    }

    @Override
    public void tell() {
        for (Server server : servers) {
            server.update(msg);
        }
    }
}
