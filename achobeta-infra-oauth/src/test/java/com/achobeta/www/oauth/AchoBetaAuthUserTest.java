package com.achobeta.www.oauth;

import com.achobeta.www.oauth.dao.AuthUserMapper;
import com.achobeta.www.oauth.entity.AuthUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class AchoBetaAuthUserTest {
    @Autowired
    private AuthUserMapper authUserMapper;
    @Test
    public void mockUser() {
        AuthUser jettcc = AuthUser.builder()
                .username("jettcc")
                .password(new BCryptPasswordEncoder().encode("123123"))
                .phone("13712345678").build();
        authUserMapper.insert(jettcc);
    }
}
