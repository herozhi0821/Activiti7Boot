package com.example.activiti7boot.modules.service.impl;

import com.example.activiti7boot.modules.bean.SysUser;
import com.example.activiti7boot.modules.dao.SysUserMapper;
import com.example.activiti7boot.modules.service.SysUserService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {
    private final static Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);


    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    private SqlSessionFactory factory;

    @Override
    public Integer[] getCheckUsers(Integer roleId) {

        return sysUserMapper.getCheckUsers(roleId);
    }

    @Override
    public SysUser selectByUserName(String userName) {
        SysUser sysUser = sysUserMapper.selectByUserName(userName);
        return sysUser;
    }

    @Override
    public SysUser selectByPhoneNumber(String phoneNumber) {
        Example example = new Example(SysUser.class);
        example.createCriteria().andEqualTo("phoneNumber", phoneNumber);
        SysUser sysUser = sysUserMapper.selectOneByExample(example);
        return sysUser;
    }

    @Override
    public SysUser selectByUser(String userName) {
        Example example = new Example(SysUser.class);
        example.createCriteria().andEqualTo("userName", userName);
        SysUser sysUser = sysUserMapper.selectOneByExample(example);
        return sysUser;
    }

    @Override
    public List<SysUser> selectUsersByUserName(String username) {
        Example example = new Example(SysUser.class);
        example.createCriteria().andEqualTo("userName", username);
        List<SysUser> sysUsers = sysUserMapper.selectByExample(example);
        ;
        return sysUsers;
    }

    @Override
    public int addSysUser(SysUser sysUser) {
        // TODO Auto-generated method stub
        return sysUserMapper.insertSelective(sysUser);
    }

    @Override
    public int updatePwdByPhone(String username, String password) {
        return sysUserMapper.updatePwdByPhone(username, password);
    }


    @Override
    public int updateDocsByPhone(String userName, String docIds) {
        // TODO Auto-generated method stub
        return sysUserMapper.updateDocsByPhone(userName, docIds);
    }

    @Override
    public int deleteCheckUser(Integer userId) {
        // TODO Auto-generated method stub
        return sysUserMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public int updateStatusById(Integer userId, Integer status) {
        SysUser user = new SysUser();
        user.setId(userId);
        user.setUserStatus(status);
        user.setForbiddenTime(new Date());
        return sysUserMapper.updateByPrimaryKeySelective(user);
    }


    @Override
    public void updateSysUserById(SysUser SysUser) {
        sysUserMapper.updateSysUserById(SysUser);
    }

    @Override
    public Integer getRoleId(String userName) {

        Integer roleId = sysUserMapper.getRoleId(userName);
        return roleId;
    }

    @Override
    public int updateCnkiNameByPhone(String userName, String cnkiName) {
        Example example = new Example(SysUser.class);
        example.createCriteria().andEqualTo("userName", userName);
        SysUser sysUser = new SysUser();
        sysUser.setCnkiName(cnkiName);
        return sysUserMapper.updateByExampleSelective(sysUser, example);
    }

    @Override
    public int updateIdentPhotoByUserName(String userName, byte[] identPhoto) {
        return sysUserMapper.updateIdentPhotoByUserName(userName, identPhoto);
    }

    @Override
    public Integer batchInsert(List<SysUser> list) {
        Integer result = 0;
        SqlSession sqlSession = factory.openSession(ExecutorType.BATCH, false);
        try {
            // 每次手动提交事务时，批次已插入数据量。如1000条一次提交。
            int batchSize = 1000;
            int size = list.size();
            for (int i = 0; i < size; i++) {
                // 调用单条插入，而非batchInsert。
                // 原因：ExecutorType.BATCH+insert并非直接插入，而是数据库预编译，等待事务提交
                SysUser sysUser = list.get(i);
                sysUserMapper.batchInsert(sysUser);
                // 批次提交事务
                if ((i > 0 && i % batchSize == 0) || (size < batchSize)) {
                    // 预插入，代替commit()和closeCache()
                    sqlSession.flushStatements();
                }
            }
        } catch (Exception e) {
            result = -1;
            logger.error("批量插入失败：" + e.toString());
            sqlSession.rollback();
        } finally {
            // 关闭事务
            sqlSession.close();
        }
        return result;
    }

    @Override
    public int updateUserById(SysUser SysUser) {
        return sysUserMapper.updateByPrimaryKeySelective(SysUser);
    }

    @Override
    public String selePhoneNumber(String userName) {
        return sysUserMapper.selePhoneNumber(userName);
    }

    @Override
    public int updatePicPathByUserName(String userName, String picPath) {
        return sysUserMapper.updatePicPathByUserName(userName, picPath);
    }

}
