package com.code.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.code.em.EnableEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ping
 */
@Data
@ApiModel("用户信息")
public class UserInfoVO implements Serializable {
    private static final long serialVersionUID = 1420225155342176259L;

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("电话号码")
    private String phoneNumber;


}
