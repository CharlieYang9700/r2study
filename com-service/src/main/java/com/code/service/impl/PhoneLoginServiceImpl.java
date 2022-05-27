package com.code.service.impl;

import com.code.pojo.domain.UserInfo;
import com.code.pojo.dto.LoginDTO;
import com.code.pojo.param.LoginParam;
import com.code.service.ILogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author : YangPing
 * @date 2022/5/5 10:30
 */
@Service
@Slf4j
public class PhoneLoginServiceImpl implements ILogin {
    @Override
    public UserInfo login(LoginDTO login) {
        log.info("########电话登录#########");
        return null;
    }
}
