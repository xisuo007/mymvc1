package com.king.controller.validation;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author ljq
 * @Date 2019/10/30 10:18
 */
@AllArgsConstructor
@Data
public class ValidBO {
    private Object forValid;
    private Valid validated;
}
