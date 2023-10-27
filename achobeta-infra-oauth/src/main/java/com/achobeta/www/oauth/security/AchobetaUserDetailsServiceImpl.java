package com.achobeta.www.oauth.security;

import com.achobeta.www.oauth.dao.AuthUserMapper;
import com.achobeta.www.oauth.entity.AchobetaUserDetails;
import com.achobeta.www.oauth.entity.AuthUser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static java.lang.StringTemplate.STR;

/**
 * <span>
 * user details service
 * </span>
 *
 * @author jettcc in 2023/10/17
 * @version 1.0
 */
@Slf4j
@Service
public class AchobetaUserDetailsServiceImpl implements ReactiveUserDetailsService {

    private final AuthUserMapper authUserMapper;

    public AchobetaUserDetailsServiceImpl(AuthUserMapper authUserMapper) {
        this.authUserMapper = authUserMapper;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        log.info(STR. "account login username: \{ username } " );
        var user = Optional.ofNullable(authUserMapper.selectOne(new QueryWrapper<AuthUser>()
                        .eq("c_username", username)))
                .orElseThrow(() -> new UsernameNotFoundException(STR. "account \{ username } not found" ));
        var authList = AuthorityUtils.commaSeparatedStringToAuthorityList("role");

        return Mono.just(new AchobetaUserDetails(
                user.getId(),
                user.getUuid(),
                user.getUsername(),
                user.getPassword(),
                user.getPhone(),
                authList,
                !user.getExpired(),
                !user.getLocked(),
                // need to use session center to judge
                true,
                user.getEnabled()
        ));
    }
}
