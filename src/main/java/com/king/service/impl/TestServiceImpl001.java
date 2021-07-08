package com.king.service.impl;

import com.king.entity.test.TaskInfo;
import com.king.mapper.TaskInfoMapper;
import com.king.service.TestService001;
import com.king.util.dao.DAO;
import com.king.util.domain.ResultDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ljq on 2021-7-2 16:48
 */
@Service
public class TestServiceImpl001 implements TestService001 {

    //private DAO<TaskInfo,Integer> taskInfoDAO;
    //@Resource
    //public void setTaskInfoDAO(DAO<TaskInfo, Integer> taskInfoDAO) {
    //    this.taskInfoDAO = taskInfoDAO;
    //    this.taskInfoDAO.setPrefix(TaskInfo.class.getName());
    //}

    @Resource
    TaskInfoMapper infoMapper;

    @Override
    public ResultDTO<List<TaskInfo>> getList() {
        TaskInfo info = new TaskInfo();
        info.setIsDelete("n");
        TaskInfo taskInfo = infoMapper.selectByPrimaryKey(2);
        ResultDTO ret = new ResultDTO();
        ret.setSuccess(true);
        ret.setData(Arrays.asList(taskInfo));
        return ret;
    }
}
