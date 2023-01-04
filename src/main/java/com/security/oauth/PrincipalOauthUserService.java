package com.security.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauthUserService extends DefaultOAuth2UserService {


    //구글로부터 받는 userRequest 데이터에 대한 후처리되는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println(userRequest.getClientRegistration());
        System.out.println(userRequest.getAccessToken());
        //구글 로그인 버튼 클릭 -> 구글로그인 창 -> 로그인을 완료 -> code를 리턴(OAuth - client라이브러리) -> AcessToken요청
        //userRequest 정보 -> loadUser함수 호출 -> 구글로부터 회원프로필 받아준다.
        System.out.println(super.loadUser(userRequest).getAttributes());
        return super.loadUser(userRequest);
    }
}
