package com.code.pojo.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : YangPing
 * @date 2022/5/5 10:20
 */
@Data
public class LoginParam implements Serializable {
    private static final long serialVersionUID = 2994092472590225906L;

    private String username;

    private String password;

    private String loginType;
}
