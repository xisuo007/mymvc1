package com.king.controller;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.king.controller.validation.Create;
import com.king.controller.validation.Update;
import com.king.lomboktest.Car;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Accessors(chain = true)
@Data
public class JsonToBean implements Serializable {

    /**
     * 通过添加自定义分组，在controller验证的时候指定分组进行区分验证
     * 注意，当类型是int型的时候，需要用原生的包装，用int的时候会有初始值0，@notnull会验证通过，用Integer的时候没有初始值！
     */
    @NotNull(message = "id不能为空")
    private Integer id;


    @NotEmpty(message = "名字不能为空")
    @Length(max = 20,message = "名字不能超过20个字符")
    @Pattern(regexp = "^[\\\\u4E00-\\\\u9FA5A-Za-z0-9\\\\*]*$",message = "名字限制：最多20个字符，包含中文，字母，数字")
    @JsonProperty("json_name")
    private String name;

    @NotNull(message = "金额不能为空")
    private BigDecimal price;

    @Future(message = "时间必须是将来时间")
    private Date createTime;

    //@NotNull(message = "list 不能为空")
    private List<Car> carList;

}
