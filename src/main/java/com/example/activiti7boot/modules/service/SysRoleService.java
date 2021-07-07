package com.example.activiti7boot.modules.service;

import com.example.activiti7boot.modules.bean.SysRole;

public interface SysRoleService {

	public SysRole getRolesByUserId(Integer userId);

	public SysRole getSysRole(String userName);
	
	SysRole getRoleById(Integer id);

}