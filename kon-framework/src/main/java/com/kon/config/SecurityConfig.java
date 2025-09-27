package com.kon.config;


import com.kon.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 基于Spring Security 5.7+的最新配置（替代WebSecurityConfigurerAdapter）
 */
@Configuration
@EnableWebSecurity // 显式启用Web安全配置
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    // 密码编码器（保持不变）
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 认证管理器（替代原authenticationManagerBean方法）
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // 核心安全过滤器链配置（替代原configure(HttpSecurity http)方法）
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 关闭CSRF保护
                .csrf(csrf -> csrf.disable())
                // 配置会话策略：无状态（JWT场景）
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 授权规则配置
                .authorizeHttpRequests(auth -> auth
                        // 登录接口允许匿名访问
                        .requestMatchers("/login").anonymous()
                        //退出登录必要要已登录
                        .requestMatchers("/logout").authenticated()
                        // 友链查询接口需要认证
//                        .requestMatchers("/link/getAllLink").authenticated()
                        // 其他所有请求允许访问
                        .anyRequest().permitAll()
                )
                // 配置异常处理器
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint) // 认证失败处理器
                        .accessDeniedHandler(accessDeniedHandler) // 授权失败处理器
                )
                // 关闭默认logout功能
                .logout(logout -> logout.disable())
                // 允许跨域
                .cors(cors -> cors.configurationSource(request -> {
                    // 使用默认跨域配置（如需自定义可在此修改）
                    return new org.springframework.web.cors.CorsConfiguration().applyPermitDefaultValues();
                }));

        // 将JWT过滤器添加到UsernamePasswordAuthenticationFilter之前
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
