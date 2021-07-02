package com.king.miaosha.service.impl;

import com.king.miaosha.service.TestKill;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ljq on 2019/6/25 11:55
 */
public class TestKillImpl implements TestKill {
    public static Map<Long, Long> inventory;
    static {
        inventory = new HashMap<>();
        inventory.put(10000001L,10000l);
        inventory.put(10000002L,10000l);
    }
    @Override
    public void secKill(String arg1, Long arg2) {
        //简单的秒杀demo
        reduceInventory(arg2);
    }

    public Long reduceInventory(Long commodityId){
        inventory.put(commodityId,inventory.get(commodityId)-1);
        return inventory.get(commodityId);
    }
}
