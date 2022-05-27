package com.code.pojo.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author ping
 */
@Data
public class LoginDTO {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 类型
     */
    private String loginType;
}
