package com.achobeta.www.service.server.impl;

import com.achobeta.www.common.util.PaginationResponse;
import com.achobeta.www.service.controller.params.QueryClassInfoParams;
import com.achobeta.www.service.controller.vo.QueryClassInfoVO;
import com.achobeta.www.service.entity.ClassInfo;
import com.achobeta.www.service.mapper.ClassInfoMapper;
import com.achobeta.www.service.server.SchoolInfoServer;
import com.achobeta.www.service.server.converter.ClassInfoConverter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <span>
 *     school info server implement
 * </span>
 *
 * @author jettcc in 2024/1/27
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SchoolInfoServerImpl implements SchoolInfoServer {
    private final ClassInfoMapper classInfoMapper;
    @Override
    public QueryClassInfoVO queryClassInfoByParams(QueryClassInfoParams params) {
        Page<ClassInfo> pagination =
                params.getPagination().get();

        var views = classInfoMapper.selectPage(pagination, null);
        return QueryClassInfoVO.builder()
                .views(views.getRecords().stream().map(
                        ClassInfoConverter.INSTANCE::entityToView
                ).toList())
                .pagination(
                    PaginationResponse.builder()
                            .total(views.getSize())
                            .page(pagination.getPages())
                            .size(pagination.getSize())
                            .build()
                )
                .build();
    }
}
