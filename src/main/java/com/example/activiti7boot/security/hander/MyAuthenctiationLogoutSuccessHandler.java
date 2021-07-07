package com.example.activiti7boot.security.hander;

import com.example.activiti7boot.common.msgreturn.ResultCode;
import com.example.activiti7boot.common.msgreturn.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录
 * @author ZhiPengyu
 *
 */
@Component
public class MyAuthenctiationLogoutSuccessHandler implements LogoutSuccessHandler{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ResultGenerator resultGenerator;
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		logger.info("退出登录！");

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(resultGenerator.getFreeResult(ResultCode.LOGOUT_SUCCESS).toString());
	}
}
