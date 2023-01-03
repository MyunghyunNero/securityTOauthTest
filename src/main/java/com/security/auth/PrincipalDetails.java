package com.security.auth;



// 시큐리티가 /login 요청이 오면 낚아채서 로그인을 진행시켜줌
//로그인 진행이 완료가 되면 시큐리티 세션 만들어줌
//오브젝트 타입  => Authentication 타입 객채
// Authentication 안에 User 정보가 있어야함
//User 오브젝트 타입 => UserDetails 타입 객체

import com.security.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

//Security Session => Authentication => UserDetails
public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    //해당 user의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    //회원이 로그인한지 오래 되었는지 확인 그리고 휴면계정
    @Override
    public boolean isEnabled() {
        return true;
    }
}
