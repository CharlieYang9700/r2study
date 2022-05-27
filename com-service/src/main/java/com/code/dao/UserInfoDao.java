package com.code.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.code.pojo.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * @author 86150
 */
@Mapper
@Repository
public interface UserInfoDao extends BaseMapper<UserInfo> {
    /**
     *  添加用户
     * @param userInfo
     */
    void insertUserInfo(UserInfo userInfo);
}
