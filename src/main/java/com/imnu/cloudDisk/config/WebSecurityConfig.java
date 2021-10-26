package com.imnu.cloudDisk.config;

import com.imnu.cloudDisk.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private  UserSecurityService userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Autowired		//下面会编写这个类
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    // 1. 配置TokenRepository
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new KaptchaAuthenticationFilter("/login", "/login?error"), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/vue/**").permitAll()
                .antMatchers("/vue/user/**").permitAll()
                .antMatchers("/vue/file/**").permitAll()
                .antMatchers("/check_code").permitAll()
                .antMatchers("/upload").permitAll()
                .antMatchers("/VerificationCode").permitAll()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/loginUI/**").permitAll()
                .antMatchers("/layuiadmin/**").permitAll()
                .antMatchers("/layui1/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/vueFile/**").permitAll()
                .antMatchers("/pic/**").permitAll()
                .antMatchers("/style/**").permitAll()
//                .antMatchers("/**").authenticated()
                .and().formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/login")
                .successForwardUrl("/index")
                .and()
                .logout()
                .and()
                //记住我的配置
                // rememberMe需要的配置包含TokenRepository对象以及token过期时间
                .rememberMe()
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(60*60*10)
                    .userDetailsService(userDetailsService)
                .and()
                .csrf().disable();
        http.headers().frameOptions().sameOrigin();
        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 设置自定义的userDetailsService
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new PasswordEncoder() {
                    @Override
                    public String encode(CharSequence rawPassword) {
                        return MD5Util.encode((String)rawPassword);
                    }

                    @Override
                    public boolean matches(CharSequence rawPassword, String encodedPassword) {
                        rawPassword = MD5Util.encode((String)rawPassword);
                        boolean result = rawPassword.equals(encodedPassword);
                        return result;
                    }
                });
    }
}
