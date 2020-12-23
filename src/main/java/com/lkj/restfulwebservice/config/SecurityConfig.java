package com.lkj.restfulwebservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override   //h2 console -> security 인증 제외
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .and()
                .csrf().disable()
                .headers().frameOptions().disable(); //header 의 frame 속성값 disable

        /**
         * 참고 ****
         * CSRF(Cross site request forgery)란 웹 사이트의 취약점을 이용하여 이용자가 의도하지 하지 않은 요청을 통한 공격을 의미합니다.
         * http 통신의 Stateless 특성을 이용하여 쿠키 정보만 이용해서 사용자가 의도하지 않은 다양한 공격들을 시도할 수 있습니다.
         * 해당 웹 사이트에 로그인한 상태로 https://xxxx.com/logout URL을 호출하게 유도하면 실제 사용자는 의도하지 않은 로그아웃을 요청하게 됩니다.
         * 실제로 로그아웃뿐만 아니라 다른 웹 호출도 가능하게 되기 때문에 보안상 위험합니다.
         * 가장 간단한 해결책으로는 CSRF Token 정보를 Header 정보에 포함하여 서버 요청을 시도하는 것입니다.
         * 스프링 시큐리티는 이러한 설정은 편리하게 설정할 수 있습니다.
         */
    }

    @Autowired   //application.yml 보다 우선순위가 높음
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("lkj")
                .password("{noop}test1234")
                .roles("USER");
    }
}
