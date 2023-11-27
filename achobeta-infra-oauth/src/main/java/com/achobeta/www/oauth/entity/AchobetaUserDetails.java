package com.achobeta.www.oauth.entity;

import cn.hutool.core.lang.Assert;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * <span>
 * user details
 * </span>
 *
 * @author jettcc in 2023/10/17
 * @version 1.0
 */
@Getter
@Setter
public class AchobetaUserDetails implements UserDetails {
    private Long id;
    private String uuid;
    private String password;
    private final String username;
    private final String phone;
    private final Set<GrantedAuthority> authorities;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    public AchobetaUserDetails(Long id,
                               String uuid,
                               String username,
                               String password,
                               String phone,
                               List<GrantedAuthority> authorities,
                               boolean accountNonExpired,
                               boolean accountNonLocked,
                               boolean credentialsNonExpired,
                               boolean enabled) {
        this.uuid = uuid;
        this.id = id;
        this.password = password;
        this.phone = phone;
        this.username = username;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities)); // 非空判断+排序
    }

    private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(new AchobetaUserDetails.AuthorityComparator());
        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }
        return sortedAuthorities;
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
        @Serial
        private static final long serialVersionUID = 600L;

        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            if (g2.getAuthority() == null) {
                return -1;
            } else {
                return g1.getAuthority() == null ? 1 : g1.getAuthority().compareTo(g2.getAuthority());
            }
        }
    }
}
