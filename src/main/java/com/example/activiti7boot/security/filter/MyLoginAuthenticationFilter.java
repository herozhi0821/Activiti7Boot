package com.example.activiti7boot.security.filter;

import com.example.activiti7boot.security.hander.MyAuthenctiationFailureHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 
 * @author ZhiPengyu
 * @ClassName:    [MyLoginAuthenticationFilter.java]
 * @Description:  [登录过滤，处理json]
 * @CreateDate:   [2020年8月13日 下午5:22:12]
 */
public class MyLoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    MyAuthenctiationFailureHandler myAuthenctiationFailureHandler;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws AuthenticationException {
    	logger.info("登录过滤，处理json!");
    	HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
    	if (!request.getMethod().equals("POST")) {//必须post登录
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        //如果是application/json类型，做如下处理
        if(request.getContentType() != null && (request.getContentType().equals("application/json;charset=UTF-8")||request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE))){
            //以json形式处理数据
            String username = null;
            String password = null;

            try {
            	//将请求中的数据转为map
                @SuppressWarnings("unchecked")
				Map<String,String> map = new ObjectMapper().readValue(request.getInputStream(), Map.class);
                username = map.get("username");
                password = map.get("password");;
            } catch (IOException e) {
                e.printStackTrace();
            }
            username = username == null ? "":username.trim();
    		password = password == null ? "":password.trim();

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

            // Allow subclasses to set the "details" property
            setDetails(servletRequest, authRequest);

            return this.getAuthenticationManager().authenticate(authRequest);
        }
        //否则使用官方默认处理方式
        return super.attemptAuthentication(request, response);
    }

}

