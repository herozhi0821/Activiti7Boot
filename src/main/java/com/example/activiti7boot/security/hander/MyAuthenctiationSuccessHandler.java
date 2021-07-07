package com.example.activiti7boot.security.hander;

import cn.hutool.core.codec.Base64;
import com.example.activiti7boot.common.msgreturn.ResultCode;
import com.example.activiti7boot.common.msgreturn.ResultGenerator;
import com.example.activiti7boot.modules.bean.SysUser;
import com.example.activiti7boot.modules.service.SysUserService;
import com.example.activiti7boot.security.jwt.JwtTokenUtil;
import com.example.activiti7boot.security.verify.MyUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录成功
 * @author ZhiPengyu
 *
 */
@Component("myAuthenctiationSuccessHandler")
public class MyAuthenctiationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ResultGenerator resultGenerator;
	@Autowired
	MyUserDetailService userDetailsService;
	@Autowired
	SysUserService sysUserService;
	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication1) throws IOException, ServletException {
		logger.info("登录成功！");
		logger.info("username:"+authentication1.getName());
        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication1.getName());
        //用户密码认证机制
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        //传入Security
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //token工具返回token值
        String access_token = jwtTokenUtil.getAccessToken(userDetails);

        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("access_token", access_token);
        
        SysUser sysuser = sysUserService.selectByUserName(authentication1.getName());
        if (sysuser.getIdentPhoto() != null) {
			sysuser.setRemarks("data:image/png;base64," +Base64.encode(sysuser.getIdentPhoto()));
		}
        tokenMap.put("sys_user", sysuser);
        
    	response.setContentType("application/json;charset=UTF-8");
    	response.setStatus(HttpStatus.OK.value());
    	response.getWriter().write(resultGenerator.getFreeResult(ResultCode.LOGIN_SUCCESS,tokenMap).toString());
	} 
	
}
