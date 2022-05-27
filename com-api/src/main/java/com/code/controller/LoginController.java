package com.code.controller;

import com.code.pojo.param.LoginParam;
import com.code.service.ILogin;
import com.code.utils.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ping
 */
@Api(tags = {"登录模块"},value = "登录模块")
@RestController
@RequestMapping("/auth/")
public class LoginController {
    @Autowired
    private ILogin loginStrategy;

    @PostMapping("login")
    public Result<String> login(@RequestBody LoginParam param){
        return Result.success();
    }
}
