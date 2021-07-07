package com.example.activiti7boot.security.hander;

import com.example.activiti7boot.common.msgreturn.ResultCode;
import com.example.activiti7boot.common.msgreturn.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 已被其他用户登录
 * @author ZhiPengyu
 *
 */
@Component
public class MyAuthenctiationSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy{
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ResultGenerator resultGenerator;
	
	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
		// TODO Auto-generated method stub
		logger.info("已被其他用户登录！");
		
	    HttpServletResponse response = event.getResponse();
	    response.setContentType("application/json;charset=UTF-8");
//	    response.setStatus(HttpStatus.BAD_REQUEST.value());
	    response.getWriter().print(resultGenerator.getFreeResult(ResultCode.SESSION_EXPIRES_OTHER_LOGIN).toString());
	    response.flushBuffer();	
	}

}
