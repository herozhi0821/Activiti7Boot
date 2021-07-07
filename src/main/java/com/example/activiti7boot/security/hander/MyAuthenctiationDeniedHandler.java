package com.example.activiti7boot.security.hander;

import com.example.activiti7boot.common.msgreturn.ResultCode;
import com.example.activiti7boot.common.msgreturn.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 无权访问
 * @author ZhiPengyu
 *
 */
@Component
public class MyAuthenctiationDeniedHandler implements AccessDeniedHandler {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ResultGenerator resultGenerator;
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		logger.info("无权访问！");
		
	    response.setContentType("application/json;charset=UTF-8");
//	    response.setStatus(HttpStatus.FORBIDDEN.value());
	    response.getWriter().write(resultGenerator.getFreeResult(ResultCode.NO_PERMISSION).toString());
	}
}