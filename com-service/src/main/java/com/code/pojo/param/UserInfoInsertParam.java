package com.code.pojo.param;

import com.code.em.EnableEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ping
 */
@Data
@ApiModel("插入用户参数")
public class UserInfoInsertParam implements Serializable {
    private static final long serialVersionUID = 1420225155342176259L;

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("电话号码")
    private String phoneNumber;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("加密的盐")
    private String salt;

    @ApiModelProperty("是否启用")
    private EnableEnum enable;

}
