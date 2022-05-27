package com.code.service.impl;

import com.code.pojo.domain.UserInfo;
import com.code.pojo.dto.LoginDTO;
import com.code.pojo.param.LoginParam;
import com.code.service.ILogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ping
 */
@Service
@Slf4j
public class NormalLoginServiceImpl implements ILogin {

    @Override
    public UserInfo login(LoginDTO login) {
        log.info("########普通登录#########");
        return null;
    }
}
