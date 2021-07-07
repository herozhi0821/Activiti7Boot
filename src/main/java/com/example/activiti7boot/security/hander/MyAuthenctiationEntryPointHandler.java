package com.example.activiti7boot.security.hander;

import com.example.activiti7boot.common.msgreturn.ResultCode;
import com.example.activiti7boot.common.msgreturn.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 未登录
 * @author ZhiPengyu
 *
 */
@Component
public class MyAuthenctiationEntryPointHandler implements AuthenticationEntryPoint{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ResultGenerator resultGenerator;
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		logger.info("未登录！");
		
        response.setContentType("application/json;charset=UTF-8");
//        response.setStatus(HttpStatus.PAYMENT_REQUIRED.value());
        response.getWriter().write(resultGenerator.getFreeResult(ResultCode.LOGIN_NO).toString());
	}
}
