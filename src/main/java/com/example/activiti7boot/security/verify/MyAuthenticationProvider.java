package com.example.activiti7boot.security.verify;

import com.example.activiti7boot.modules.bean.SysRole;
import com.example.activiti7boot.modules.bean.SysUser;
import com.example.activiti7boot.modules.service.SysRoleService;
import com.example.activiti7boot.modules.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author ZhiPengyu
 * @ClassName:    [MyAuthenticationProvider.java]
 * @Description:  [登录校验-验证部分]
 * @CreateDate:   [2020年8月11日 上午10:47:30]
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
	Logger logger = LoggerFactory.getLogger(MyAuthenticationProvider.class);

    @Autowired
    MyUserDetailService userService;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysRoleService roleService;
    @Autowired
    PasswordEncoder passwordEncoder;

    
    /**
     * 去除密码验证并授权
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	logger.info("登录授权部分！");
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        //1、调用数据库获取用户信息
        if(StringUtils.isEmpty(username)) {
        	logger.error("用户名不能为空！");
        	throw new UsernameNotFoundException("用户名不能为空！");
        }
        
        //通过用户名获取用户信息并检测
        SysUser sysuser =  null;
		try {
			sysuser =sysUserService.selectByUserName(username);
		} catch (Exception e) {
			logger.error(e.toString());
			throw new UsernameNotFoundException("系统异常！");
		}
        if (sysuser == null){
        	logger.error("登录用户"+username+"不存在！");
            throw new UsernameNotFoundException("用户名有误！");//登录用户不存在！
        }

        //2、获取用户的角色
        SysRole role = roleService.getRolesByUserId(sysuser.getRoleId());
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleCode()));//角色必须以`ROLE_`开头
        UserDetails user = new User(sysuser.getUserName(),sysuser.getPassWord(),grantedAuthorities);
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
        	logger.error("登录用户密码错误！");
            throw new DisabledException("您输入的密码有误！");
        }
        
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), authorities);//密码为未加密
    }
 
    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }
}
