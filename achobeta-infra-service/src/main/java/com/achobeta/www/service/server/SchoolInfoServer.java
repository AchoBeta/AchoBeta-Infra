package com.achobeta.www.service.server;

import com.achobeta.www.service.controller.params.QueryClassInfoParams;
import com.achobeta.www.service.controller.vo.QueryClassInfoVO;

/**
 * @author jettcc in 2024/1/27
 * @version 1.0
 */
public interface SchoolInfoServer {

    QueryClassInfoVO queryClassInfoByParams(QueryClassInfoParams params);
}
