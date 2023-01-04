package com.security.controller;


import com.security.auth.PrincipalDetails;
import com.security.model.User;
import com.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test/login")
    public @ResponseBody String testLogin(Authentication authentication,
                                          @AuthenticationPrincipal UserDetails userDetails){
        System.out.println("test/login ======");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("authentication = "+principalDetails.getUsername());
        System.out.println("userDetail = " + userDetails.getUsername());
        return "세션 정보 확인";

    }
    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOAuthLogin(Authentication authentication,
                                          @AuthenticationPrincipal OAuth2User oAuth){
        System.out.println("test/oauth/login ======");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("authentication = "+oAuth2User.getAttributes());
        System.out.println("oAuth = " + oAuth.getAttributes());
        return "Oauth 세션 정보 확인";

    }
    @GetMapping({"","/"})
    public String index(){
        return "index";
    }


    //Aouth , 일반 로그인 둘다 가능 이유: principalDetail 에 UserDetails,OAuth2User 둘 다 상속 받았기 때문 -> 훨씬 편리
    @GetMapping("/user")
    @ResponseBody
    public String user(@AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println("principal : " + principalDetails.getUsername());
        return "user";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin() { return "admin";}

    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user){

        user.setRole("ROLE_USER");
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user);  //비밀번호 암호화 과정 필요
        System.out.println(user);
        return "redirect:/loginForm";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginform";
    }
}
