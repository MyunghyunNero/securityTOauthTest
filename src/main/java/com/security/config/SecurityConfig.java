package com.security.config;

import com.security.oauth.PrincipalOauthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
//1.코드받(인증) 2. 엑세스토(권한)
//3.사용자 프로필 가져오기 4-1.그 정보 토대로 회원가입 진행
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private PrincipalOauthUserService principalOauthUserService;
    @Bean  //해당 메서의 리턴되는 오브젝트를 ioc로 등록해준다
    public BCryptPasswordEncoder encoderPwd(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()//admin만 들어 갈 수 있음
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("").authenticated()
                        .requestMatchers("/").authenticated()
                        .requestMatchers("/user/**").authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN")  //시큐리티는 role변수에 ROLE_* 규칙으로 적어야함 그리고 hasRole()에는 ROLE_뺴고 뒤에 적어야
                        .anyRequest().permitAll()
                )
                .formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/login") //로그인 주소가 호출이 되면 시큐리티가 낚아채서 로그인 진행
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/loginForm")
                .userInfoEndpoint()
                .userService(principalOauthUserService);//구글 로그인 후처리 필요;


        return http.build();
    }

//    @Bean
//    public SecurityFilterChain formLoginFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().authenticated()
//                )
//                .formLogin()
//                .loginPage("/login");
//        return http.build();
//    }
}
