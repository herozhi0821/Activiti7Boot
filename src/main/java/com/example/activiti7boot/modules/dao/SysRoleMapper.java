package com.example.activiti7boot.modules.dao;

import com.example.activiti7boot.modules.bean.SysRole;
import com.example.activiti7boot.modules.tk.MyMapper;

public interface SysRoleMapper extends MyMapper<SysRole> {
	public SysRole getSysRole(String userName);
	SysRole getRoleById(Integer id);
}