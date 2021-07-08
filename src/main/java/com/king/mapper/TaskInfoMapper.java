package com.king.mapper;

import com.king.entity.test.TaskInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by ljq on 2021-7-2 18:04
 */
@Mapper
public interface TaskInfoMapper {
    TaskInfo selectByPrimaryKey(Integer id);
}
