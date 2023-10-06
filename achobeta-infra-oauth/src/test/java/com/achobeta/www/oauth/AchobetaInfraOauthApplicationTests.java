package com.achobeta.www.oauth;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class AchobetaInfraOauthApplicationTests {

    @Test
    void contextLoads() {
        log.info("这是一个日志埋点");
        log.warn("这是一个警告");
        log.error("这是一个错误");
        log.debug("这是一个debug");
        log.trace("这是一个链式追踪");
    }

}
