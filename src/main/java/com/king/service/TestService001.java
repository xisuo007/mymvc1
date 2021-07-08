package com.king.service;

import com.king.entity.test.TaskInfo;
import com.king.util.domain.ResultDTO;

import java.util.List;

/**
 * Created by ljq on 2021-7-2 16:46
 */
public interface TestService001 {
    ResultDTO<List<TaskInfo>> getList();
}
