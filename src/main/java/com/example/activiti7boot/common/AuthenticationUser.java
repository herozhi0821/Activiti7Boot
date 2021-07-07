package com.example.activiti7boot.common;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 
 * @author ZhiPengyu
 * @ClassName:    [AuthenticationUser.java]
 * @Description:  [获取当前系统用户信息]
 * @CreateDate:   [2020年9月24日 上午9:31:50]
 */
@Component
public class AuthenticationUser {
	
	/**
	 * 获取当前用户的用户名，适用于验证接口
	 * 验证接口下anonymousUser为无角色访问-无权
	 * 开放权限下均为anonymousUser-正常
	 * @return
	 * @throws MyException 
	 */
	public String getActiveUser(){
		try {
			String userString = SecurityContextHolder.getContext().getAuthentication().getName();
			return userString;
		} catch (Exception e) {
			return null;
		}
	}
	
}
