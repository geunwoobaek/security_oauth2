package com.cos.security1.config.auth;

import com.cos.security1.Role;
import com.cos.security1.User;
import com.cos.security1.UserRepository;
import com.cos.security1.config.auth.provider.FacebookUserInfo;
import com.cos.security1.config.auth.provider.GoogleUserInfo;
import com.cos.security1.config.auth.provider.Oauth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    //후처리함수
    //userRequest ->loadUser->회원 프로필
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        System.out.println("userRequest:"+userRequest);
        System.out.println("getAccessToken:"+userRequest.getAccessToken());
        System.out.println("getClientRegistration:"+userRequest.getClientRegistration());

        OAuth2User oAuth2User=super.loadUser(userRequest);
        System.out.println("userRequest:"+oAuth2User.getAttributes());
        Oauth2UserInfo oauth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            System.out.println("구글 로그인 요청");
            oauth2UserInfo=new GoogleUserInfo(oAuth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
            System.out.println("페이스북 로그인 요청");
            oauth2UserInfo=new FacebookUserInfo(oAuth2User.getAttributes());
        }
        String provider=oauth2UserInfo.getProvider(); //google
        String providerId=oauth2UserInfo.getProviderId();
        String username=provider+providerId;
        String email=oauth2UserInfo.getEmail();

        User userEntity=userRepository.findByUsername(username);
        if(userEntity==null){
            userEntity=new User().builder()
                    .username(username)
                    .email(email)
                    .username(username)
                    .role(Role.ROLE_USER)
                    .build();
            userRepository.save(userEntity);
        }
        //return super.loadUser(userRequest);
        return new PrincipalDetails(userEntity,oAuth2User.getAttributes());
    }
}
