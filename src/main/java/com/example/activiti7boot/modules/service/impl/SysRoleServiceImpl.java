package com.example.activiti7boot.modules.service.impl;

import com.example.activiti7boot.modules.bean.SysRole;
import com.example.activiti7boot.modules.dao.SysRoleMapper;
import com.example.activiti7boot.modules.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl implements SysRoleService {

	@Autowired
	SysRoleMapper sysRoleMapper;
	
	@Override
	public SysRole getRolesByUserId(Integer roleId) {
		SysRole sysRole =  sysRoleMapper.selectByPrimaryKey(roleId);
		return sysRole;
	}

	@Override
	public SysRole getSysRole(String userName) {
		SysRole sysRole =  sysRoleMapper.getSysRole(userName);
		return sysRole;
	}

	@Override
	public SysRole getRoleById(Integer id) {
		SysRole sysRole = sysRoleMapper.getRoleById(id);
		return sysRole;
	}

}
