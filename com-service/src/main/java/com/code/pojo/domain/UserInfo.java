package com.code.pojo.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.code.em.EnableEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ping
 */
@Data
@TableName("user_info")
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1420225155543502867L;


    @TableId("id")
    private Long id;

    @TableField("username")
    private String username;

    @TableField("phone_number")
    private String phoneNumber;

    @TableField("password")
    private String password;

    @TableField("salt")
    private String salt;

    @TableField("enable")
    private EnableEnum enable;

    public Integer getEnable() {
        return enable.getValue();
    }

    public void setEnable(Integer enable) {
        if(1==enable){
            this.enable=EnableEnum.ENABLE;
        }
        if(0==enable) {
            this.enable=EnableEnum.UNABLE;
        }

    }
    public void setEnable(EnableEnum enable) {
        this.enable=enable;
    }
}
