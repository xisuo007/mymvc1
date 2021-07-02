package com.king.util.validation;

import lombok.Data;

import java.util.Iterator;
import java.util.Map;

/**
 * @description: 验证结果信息
 **/
@Data
public class ValidationResultDTO {
    //是否验证失败
    private boolean hasErrors;
    //校验错误信息
    private String errMsg;
    public void setErrMsg(Map<String, String> errorMap) {
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = errorMap.entrySet().iterator();
        Map.Entry<String, String> entry = null;
        while (iterator.hasNext()) {
            entry = iterator.next();
            sb.append(entry.getValue()).append(";");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        this.errMsg = sb.toString();
    }
}
