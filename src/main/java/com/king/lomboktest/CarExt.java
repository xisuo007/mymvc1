package com.king.lomboktest;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author ljq
 * @Date 2019/10/23 14:32
 */
@Accessors(chain = true)
@Data
public class CarExt extends CarChain {
    private String year;
}
