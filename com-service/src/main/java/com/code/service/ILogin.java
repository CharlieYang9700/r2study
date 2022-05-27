package com.code.service;

import com.code.pojo.domain.UserInfo;
import com.code.pojo.dto.LoginDTO;
import com.code.pojo.param.LoginParam;

/**
 * @author : YangPing
 * @date 2022/4/11 23:43
 */
public interface ILogin {
    /**
     * 策略接口
     * @param param
     * @return
     */
    UserInfo login(LoginDTO param);
}
