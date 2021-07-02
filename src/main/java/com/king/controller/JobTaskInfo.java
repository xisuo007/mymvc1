package com.king.controller;

import lombok.Data;

/**
 * Created by ljq on 2020/5/11 12:00
 */
@Data
public class JobTaskInfo {
    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务分组标识
     */
    private String taskGroup;
}
