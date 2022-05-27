package com.code.service.impl;

import com.code.pojo.domain.UserInfo;
import com.code.pojo.dto.LoginDTO;
import com.code.service.ILogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author : YangPing
 * @date 2022/5/5 10:53
 */
@Service
@Slf4j
public class EmailLoginServiceImpl implements ILogin {
    @Override
    public UserInfo login(LoginDTO param) {
        log.info("########邮件登录#########");
        return null;
    }
}
