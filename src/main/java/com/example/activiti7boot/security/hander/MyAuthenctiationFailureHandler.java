package com.example.activiti7boot.security.hander;

import com.example.activiti7boot.common.msgreturn.ResultCode;
import com.example.activiti7boot.common.msgreturn.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败
 * @author ZhiPengyu
 *
 */
@Component("myAuthenctiationFailureHandler")
public class MyAuthenctiationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ResultGenerator resultGenerator;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		logger.info("登录失败！");
		SecurityContextHolder.clearContext();
		response.setContentType("application/json;charset=UTF-8");
//		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.getWriter().write(resultGenerator.getFreeResult(ResultCode.LOGIN_FAIL,exception.getMessage()).toString());
	}
}
