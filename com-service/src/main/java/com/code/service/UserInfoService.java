package com.code.service;

import com.code.pojo.domain.UserInfo;
import com.code.pojo.param.UserInfoInsertParam;
import com.code.pojo.vo.UserInfoVO;

import java.util.List;

/**
 * @author ping
 */
public interface UserInfoService {
    /**
     * 插入用户
     * @param param
     */
    void insertUserInfo(UserInfoInsertParam param);

    /**
     * 查询全部用户信息
     * @return
     */
    List<UserInfoVO> selectUser();
}
