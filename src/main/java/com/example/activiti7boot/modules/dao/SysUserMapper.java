package com.example.activiti7boot.modules.dao;

import com.example.activiti7boot.modules.bean.SysUser;
import com.example.activiti7boot.modules.tk.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysUserMapper extends MyMapper<SysUser> {

    public int updatePwdByPhone(@Param("userName") String userName, @Param("passWord") String passWord);

    public int updateDocsByPhone(@Param("userName") String userName, @Param("docIds") String docIds);


    public void updateSysUserById(SysUser SysUser);

    public Integer[] getCheckUsers(Integer roleId);

    public Integer getRoleId(String userName);

    public SysUser selectByUserName(String userName);

    public int updateIdentPhotoByUserName(String userName, byte[] identPhoto);

    public int updatePicPathByUserName(String userName, String picPath);

    public void batchInsert(SysUser sysUser);

    @Select("select phone_number phoneNumber from sys_user where user_name = #{userName}")
    public String selePhoneNumber(@Param(value = "userName") String userName);

    public List<SysUser> getCommonUsersOfSchool(SysUser user);
}