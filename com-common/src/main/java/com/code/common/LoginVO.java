package com.code.common;

import com.code.em.GenderEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author ping
 */
@Data
@ApiModel("user")
public class LoginVO implements Serializable {
    private static final long serialVersionUID= -57338362947965567L;

    @ApiModelProperty("账号的id")
    private Long id;

    @ApiModelProperty("账号的电话号码")
    private String account;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("性别（0：女；1：男；2：未知）")
    private GenderEnum gender;

    @ApiModelProperty("出生日期")
    private LocalDate birthday;

    @ApiModelProperty("token")
    private String token;
}
