package com.cos.security1.config.auth;

import com.cos.security1.User;
import com.cos.security1.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
 //시큐리티 설정에서 loginProcessingUrl("/login")l
 //login요청이 오면 자동으로 UserDetailsService 타입으로 Ioc되어있는 loadByUserName함수가 실행
@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    //loadUserByUsername이 종료될때 @AuthenticationPrincipipal 어노테이션이 만들어진다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity=userRepository.findByUsername(username);
        if(userEntity!=null) return new PrincipalDetails(userEntity);
        return null;
    }
}
