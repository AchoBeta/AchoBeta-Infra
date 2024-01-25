package com.achobeta.www.service.mapper;

import com.achobeta.www.service.entity.ClassInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author jett
 * @since 2024/1/25
 */
@Mapper
@Repository
public interface ClassInfoMapper extends BaseMapper<ClassInfo> {
}
