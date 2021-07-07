package com.example.activiti7boot.security.config;

import com.example.activiti7boot.security.filter.JwtAuthenticationTokenFilter;
import com.example.activiti7boot.security.filter.MyLoginAuthenticationFilter;
import com.example.activiti7boot.security.filter.SecurityCorsFilter;
import com.example.activiti7boot.security.hander.*;
import com.example.activiti7boot.security.verify.MyAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	MyAuthenctiationSuccessHandler myAuthenctiationSuccessHandler;//登录成功
	@Autowired
	MyAuthenctiationFailureHandler myAuthenctiationFailureHandler;//登录失败
	@Autowired
	MyAuthenctiationDeniedHandler myAuthenctiationDeniedHandler;//无权访问
	@Autowired
	MyAuthenctiationEntryPointHandler myAuthenctiationEntryPointHandler;//未登录
	@Autowired
	MyAuthenctiationInvalidSessionStrategy mMyAuthenctiationInvalidSessionStrategy;//session到期
	@Autowired
	MyAuthenctiationLogoutSuccessHandler myAuthenctiationLogoutSuccessHandler;//退出成功
	@Autowired
	MyAuthenctiationSessionInformationExpiredStrategy myAuthenctiationSessionStrategy;//session到期,被登陆
	@Autowired
	JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;//访问过滤-用于访问校验jwt
	@Autowired
	MyLogoutHandler logoutHandler;
	
	@Value("${login.pass.permitAll}")
	String[] permitAll;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable().cacheControl();
		http.cors().and().csrf().disable()
			.authorizeRequests()
				.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()//让Spring security放行所有preflight request 
				.requestMatchers(CorsUtils::isCorsRequest).permitAll()
				.antMatchers(permitAll).permitAll()
				.anyRequest().authenticated()
				.and()
			.exceptionHandling()
				.authenticationEntryPoint(myAuthenctiationEntryPointHandler)//未登录402
				.accessDeniedHandler(myAuthenctiationDeniedHandler)//无权访问403
				.and()
			.formLogin()
				.loginProcessingUrl("/defaultLogin")
				.successHandler(myAuthenctiationSuccessHandler)
				.failureHandler(myAuthenctiationFailureHandler)
				.permitAll()
				.and()
			.logout().disable()
//				.logoutSuccessHandler(myAuthenctiationLogoutSuccessHandler)//退出登陆200
//				.deleteCookies("JSESSIONID")
//				.permitAll()//退出
//				.and()
			.requestCache().disable();//使退出前的操作请求缓存为空失效，但是并没有更改获取缓存路径并跳转的实现，避免登录后跳转到上一次操作嗯对全路径下而非主页
		
		//登录json
		http.addFilterBefore(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		//session换jwt
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterAfter(jwtAuthenticationTokenFilter, MyLoginAuthenticationFilter.class);//访问过滤
		//跨域过滤器
		http.addFilterBefore(WebSecurityCorsFilter(), ChannelProcessingFilter.class);
		
	}
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	//加入中间验证层，可实现自定义验证用户等信息
	@Bean
    public AuthenticationProvider authenticationProvider() {
        AuthenticationProvider provider = new MyAuthenticationProvider();
        return provider;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
    
    //登陆处理
	@Bean
	public MyLoginAuthenticationFilter loginAuthenticationFilter() throws Exception {
		MyLoginAuthenticationFilter filter = new MyLoginAuthenticationFilter();
        filter.setAuthenticationFailureHandler(myAuthenctiationFailureHandler);
        filter.setAuthenticationSuccessHandler(myAuthenctiationSuccessHandler);
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }
	
	//跨域设置过滤
    @Bean
	public SecurityCorsFilter WebSecurityCorsFilter() throws Exception {
    	SecurityCorsFilter filter = new SecurityCorsFilter();
        return filter;
    }
}