package com.code.controller;

import cn.hutool.crypto.SecureUtil;
import com.code.annotation.Login;
import com.code.pojo.param.UserInfoInsertParam;
import com.code.pojo.vo.UserInfoVO;
import com.code.service.UserInfoService;
import com.code.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.code.utils.Result.success;

@Login
@Api(tags = {"测试服务"},description = "测试服务")
@RestController
@RequestMapping("admin")
@Slf4j
public class TestController {
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("test")
    @ApiOperation(value = "测试接口")
    public Result<String> test(){
        return success("成功返回");
    }
    @GetMapping("login")
    @ApiOperation(value = "登录接口")
    public Result<String> login(){
        return success("失败返回");
    }

    @ApiOperation("插入用户接口:完成")
    @PostMapping("insert")
    public Result<String> insert(UserInfoInsertParam param){
        userInfoService.insertUserInfo(param);
        return success();
    }


    @ApiOperation("查询用户")
    @GetMapping("select")
    public Result<List<UserInfoVO>> selectUser(){
        log.info("++++++++test_alibaba++++++++");
        return success(userInfoService.selectUser());
    }

    @ApiOperation("生成密码")
    @GetMapping("generate-password")
    public Result<ArrayList<Map>> generatePassword(){
        ArrayList<Map> maps = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put(String.valueOf(i),SecureUtil.md5("123456"));
            maps.add(map);
        }
        return Result.success(maps);
    }
}
