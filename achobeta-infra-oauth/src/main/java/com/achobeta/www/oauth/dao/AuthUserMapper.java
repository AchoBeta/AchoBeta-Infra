package com.achobeta.www.oauth.dao;

import com.achobeta.www.oauth.entity.AuthUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <span>
 *     user dao
 * </span>
 *
 * @author jettcc in 2023/10/18
 * @version 1.0
 */
@Mapper
public interface AuthUserMapper extends BaseMapper<AuthUser> {
}
