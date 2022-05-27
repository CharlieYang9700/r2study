package com.code.service.impl;

import com.code.service.ILogin;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : YangPing
 * @date 2022/4/11 23:48
 */
public class LoginStrategyFactory {

    private final static Map<String, ILogin> LOGIN_STRATEGY_MAP = new HashMap<>(3);

    private final static String DEFAULT_KEY = LoginTypeKey.NORMAL;


    static {
        LOGIN_STRATEGY_MAP.put(LoginTypeKey.NORMAL,new NormalLoginServiceImpl());
        LOGIN_STRATEGY_MAP.put(LoginTypeKey.PHONE,new PhoneLoginServiceImpl());
        LOGIN_STRATEGY_MAP.put(LoginTypeKey.EMAIL,new NormalLoginServiceImpl());
    }

    public static ILogin getLoginStance(String key){
        if(LOGIN_STRATEGY_MAP.containsKey(key)){
            return LOGIN_STRATEGY_MAP.get(key);
        }
        return LOGIN_STRATEGY_MAP.get(DEFAULT_KEY);
    }


    private interface LoginTypeKey{
        String NORMAL = "normal";
        String PHONE = "phone";
        String EMAIL = "email";
    }
}
