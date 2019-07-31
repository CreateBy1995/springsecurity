package main.login.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/** 
 * @Description:  security自定义配置类
 * @Author: 1995
 * @Date: 2019/8/1 
 */ 
@EnableWebSecurity
public class CustomSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationProvider customAuthenticationProvider ;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                // 使用自定义的token验证
                .authenticationProvider(customAuthenticationProvider);
//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("aa").password(new BCryptPasswordEncoder().encode("aa")).roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 表单登录的相关配置
                .formLogin()
                    // 用户进行登录的界面
                    .loginPage("/index")
                    // 表单过滤器进行拦截的请求，只有表单登录界面的action的值和loginProcessingUrl一致时，
                    // 表单过滤器才会进行拦截,/doLogin实际上只是一个约定的映射地址而已，这个请求不必真正存在
                    // 也就是说Controller层中不需要有对应这个请求的方法
                    .loginProcessingUrl("/doLogin")
                    .and()
                // 拦截请求的相关配置
                .authorizeRequests()
                    // /index这个url地址 开放所有访问权限
                    // 需要对用户登录的界面进行放行，不然会一直被重定向
                    // 并且这个需要配在下面的anyRequest之前，因为security在查看访问权限是按照这个配置的顺序的
                    // 比如说在这个顺序下 security会先将请求匹配到到/index下，然后获取他的访问权限，发现是permitAll 就会放行
                    // 而如果index配置在anyRequest之后 则security会先将请求匹配到 anyRequest 也就是所有请求
                    // 然后发现是权限是authenticated 然后就会一直重定向
                    .antMatchers("/index").permitAll()
                    .anyRequest().authenticated();
                    // 所有url都进行拦截
    }
}
