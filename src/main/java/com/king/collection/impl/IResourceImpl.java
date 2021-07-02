package com.king.collection.impl;

import com.king.collection.resource.IResourceMap;
import org.springframework.stereotype.Service;

/**
 * Created by ljq on 2021-7-1 17:59
 */
@Service
public class IResourceImpl implements IResourceMap {
    @Override
    public String get(String str) {
        return str;
    }
}
