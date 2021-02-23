package com.cos.security1.config.auth;

import com.cos.security1.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

//시큐리티가 /login 주소 요청이 오면 낚아채서 로그인 진행시킨다.
//로그인을 진행이 완료가 되면 시큐리티 session을 만들어 줍니다.
//오브젝트=>Authentication 타입객체
//Authentication안에 정보가 있어야 됨.
//User 오브젝트 타입=>UserDetails 타입 객체
//Security Session=>Authentication=>UserDetails(PrincipalDetails)
@Getter
@AllArgsConstructor
public class PrincipalDetails implements UserDetails, OAuth2User {
    private User user;
    private Map<String,Object> attributes;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    public PrincipalDetails(User user){
        this.user=user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      Collection<GrantedAuthority> collect=new ArrayList<>();
      collect.add(new GrantedAuthority() {
          @Override
          public String getAuthority() {
              System.out.println(user);
              return user.getRole().getDescription();
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

    @Override
    public boolean isEnabled() {
        //휴먼 계정인지 아닌지 판단
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
