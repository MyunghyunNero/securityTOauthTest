package com.security.oauth;

import com.security.auth.PrincipalDetails;
import com.security.model.User;
import com.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauthUserService extends DefaultOAuth2UserService {


    @Autowired
    private UserRepository userRepository;
    //구글로부터 받는 userRequest 데이터에 대한 후처리되는 함수
    //함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println(userRequest.getClientRegistration());
        System.out.println(userRequest.getAccessToken());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        //구글 로그인 버튼 클릭 -> 구글로그인 창 -> 로그인을 완료 -> code를 리턴(OAuth - client라이브러리) -> AcessToken요청
        //userRequest 정보 -> loadUser함수 호출 -> 구글로부터 회원프로필 받아준다.
        System.out.println(super.loadUser(userRequest).getAttributes());


        //강제 회원가입
        String provider = userRequest.getClientRegistration().getClientId(); //google
        String providerId = oAuth2User.getAttribute("sub");
        String username = provider + "_" + providerId;
        String email = oAuth2User.getAttribute("email");
        String role = "ROLE_UESR";

        User user = userRepository.findByUsername(username);
        if(user == null){
            user = User.builder()
                    .username(username)
                    .email(email)
                    .role(role)
                    .build();
            userRepository.save(user);
        }
        return new PrincipalDetails(user , oAuth2User.getAttributes());
    }
}
