package com.example.activiti7boot.modules.service;

import com.example.activiti7boot.modules.bean.SysUser;

import java.util.List;

public interface SysUserService {

    public SysUser selectByUserName(String username);

    public SysUser selectByPhoneNumber(String phoneNumber);

    public List<SysUser> selectUsersByUserName(String username);

    public int addSysUser(SysUser sysUser);

    public int updatePwdByPhone(String username, String password);

    public Integer[] getCheckUsers(Integer roleId);

    public int updateDocsByPhone(String userName, String docIds);

    public int deleteCheckUser(Integer userId);


    public int updateStatusById(Integer userId, Integer status);
    
    public void updateSysUserById(SysUser SysUser);

    public Integer getRoleId(String userName);

    public SysUser selectByUser(String userName);

    public int updateCnkiNameByPhone(String userName, String cnkiName);

    public int updateIdentPhotoByUserName(String userName, byte[] identPhoto);

    public int updatePicPathByUserName(String userName, String picPath);

    public int updateUserById(SysUser SysUser);

    public Integer batchInsert(List<SysUser> sysUsers);


    public String selePhoneNumber(String userName);

}