package com.code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.code.dao.UserInfoDao;
import com.code.em.EnableEnum;
import com.code.pojo.domain.UserInfo;
import com.code.pojo.param.UserInfoInsertParam;
import com.code.pojo.vo.UserInfoVO;
import com.code.service.UserInfoService;
import com.code.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author ping
 */
@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public void insertUserInfo(UserInfoInsertParam param) {
        userInfoDao.insert(this.transform(param));
    }

    @Override
    public List<UserInfoVO> selectUser() {
        LambdaQueryWrapper<UserInfo> query = new LambdaQueryWrapper<>();
        query.eq(UserInfo::getEnable,EnableEnum.ENABLE);
        return this.transform(userInfoDao.selectList(query));
    }

    private UserInfo transform(UserInfoInsertParam param){
        if(Objects.isNull(param)){
            return null;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setId(param.getId());
        userInfo.setUsername(param.getUsername());
        userInfo.setPassword(param.getPassword());
        userInfo.setSalt(param.getSalt());
        userInfo.setPhoneNumber(param.getPhoneNumber());
        userInfo.setEnable(param.getEnable());
        return userInfo;
    }

    private List<UserInfoVO> transform(List<UserInfo> userInfos){
        if(CommonUtil.isEmpty(userInfos)){
            return null;
        }
        List<UserInfoVO> userInfoVOS = new ArrayList<>();
        for (UserInfo userInfo : userInfos) {
            UserInfoVO userInfoVO = new UserInfoVO();
            userInfoVO.setId(userInfo.getId());
            userInfoVO.setPhoneNumber(userInfo.getPhoneNumber());
            userInfo.setUsername(userInfo.getUsername());
            userInfoVOS.add(userInfoVO);
        }
        return userInfoVOS;
    }
}
