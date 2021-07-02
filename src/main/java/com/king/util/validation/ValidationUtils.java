package com.king.util.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @description: 验证工具
 **/
public class ValidationUtils {
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> ValidationResultDTO validateEntity(T obj) {
        ValidationResultDTO result = new ValidationResultDTO();
        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
        if (set != null && set.size() > 0) {
            result.setHasErrors(true);
            Map<String, String> errorMsg = new HashMap<String, String>();
            for (ConstraintViolation<T> cv : set) {
                errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());
            }
            result.setErrMsg(errorMsg);
        }
        return result;
    }

    public static <T> ValidationResultDTO validateProperty(T obj, String propertyName) {
        ValidationResultDTO result = new ValidationResultDTO();
        Set<ConstraintViolation<T>> set = validator.validateProperty(obj, propertyName, Default.class);
        if (set != null && set.size() > 0) {
            result.setHasErrors(true);
            Map<String, String> errorMsg = new HashMap<String, String>();
            for (ConstraintViolation<T> cv : set) {
                errorMsg.put(propertyName, cv.getMessage());
            }
            result.setErrMsg(errorMsg);
        }
        return result;
    }

}
