package com.example.activiti7boot.security.hander;

import com.example.activiti7boot.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MyLogoutHandler implements LogoutHandler {

	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		// TODO Auto-generated method stub
		String token = request.getHeader("Authorization").substring("Bearer ".length());
		String userString = jwtTokenUtil.getUserNameFromToken(token);
		System.out.println(userString);
	}

}
