package com.example.activiti7boot.security.verify;

import com.example.activiti7boot.modules.bean.SysRole;
import com.example.activiti7boot.modules.bean.SysUser;
import com.example.activiti7boot.modules.service.SysRoleService;
import com.example.activiti7boot.modules.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * 
 * @author ZhiPengyu
 * @ClassName:    [MyUserDetailService.java]
 * @Description:  [自定义实现加载用户信息]
 * @CreateDate:   [2020年8月11日 上午10:46:22]
 */
@Component
public class MyUserDetailService implements UserDetailsService {
	Logger logger = LoggerFactory.getLogger(MyUserDetailService.class);
	
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	logger.info("获取用户及角色！");

        //通过用户名获取用户信息
        SysUser user =  null;
		try {
			user =sysUserService.selectByUserName(username);
		} catch (Exception e) {
			logger.error(e.toString());
			throw new UsernameNotFoundException("系统异常！");
		}
        
        if (user == null){
        	logger.error("登录用户"+username+"不存在！");
            throw new UsernameNotFoundException("登录用户不存在！");
        }
       SysRole role = roleService.getRolesByUserId(user.getRoleId());
        //获取用户的角色
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleCode()));//角色必须以`ROLE_`开头
        
        return new org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getPassWord(),//若入库密码已进行加密，此处则不需要解密
                grantedAuthorities);
    }
    
}