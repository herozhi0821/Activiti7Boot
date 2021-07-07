package com.example.activiti7boot.modules.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;

public class SysUser {
    @Id
    private Integer id;

    private String userName;

    private String passWord;

    private String company;

    private Integer userStatus;

    private Integer uploadAmount;

    private Integer uploadResidue;

    private Integer uploadFail;

    private String phoneNumber;

    private String eMail;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createTime;

    private Integer roleId;

    private Date forbiddenTime;

    private String cnkiName;

    private Integer docIds;

    private String studentNum;

    private String college;

    private String major;

    private String degree;

    private String realName;

    private byte[] identPhoto;
    private String remarks;

    private String sign;
    private String picPath;

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public byte[] getIdentPhoto() {
        return identPhoto;
    }

    public void setIdentPhoto(byte[] identPhoto) {
        this.identPhoto = identPhoto;
    }

    public String getCnkiName() {
        return cnkiName;
    }

    public void setCnkiName(String cnkiName) {
        this.cnkiName = cnkiName;
    }

    public Integer getDocIds() {
        return docIds;
    }

    public void setDocIds(Integer docIds) {
        this.docIds = docIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord == null ? null : passWord.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getUploadAmount() {
        return uploadAmount;
    }

    public void setUploadAmount(Integer uploadAmount) {
        this.uploadAmount = uploadAmount;
    }

    public Integer getUploadResidue() {
        return uploadResidue;
    }

    public void setUploadResidue(Integer uploadResidue) {
        this.uploadResidue = uploadResidue;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail == null ? null : eMail.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Date getForbiddenTime() {
        return forbiddenTime;
    }

    public void setForbiddenTime(Date forbiddenTime) {
        this.forbiddenTime = forbiddenTime;
    }

    public Integer getUploadFail() {
        return uploadFail;
    }

    public void setUploadFail(Integer uploadFail) {
        this.uploadFail = uploadFail;
    }

    public SysUser() {
        super();
        // TODO Auto-generated constructor stub
    }

    public SysUser(String userName, String passWord, Integer userStatus, Integer uploadAmount, Integer uploadResidue,
                   String phoneNumber, Integer roleId) {
        super();
        this.userName = userName;
        this.passWord = passWord;
        this.userStatus = userStatus;
        this.uploadAmount = uploadAmount;
        this.uploadResidue = uploadResidue;
        this.phoneNumber = phoneNumber;
        this.roleId = roleId;
    }


    public SysUser(Integer id, String userName, String passWord, String company, Integer userStatus,
                   Integer uploadAmount, Integer uploadResidue, Integer uploadFail, String phoneNumber, String eMail,
                   Date createTime, Integer roleId, Date forbiddenTime, Integer docIds) {
        super();
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.company = company;
        this.userStatus = userStatus;
        this.uploadAmount = uploadAmount;
        this.uploadResidue = uploadResidue;
        this.uploadFail = uploadFail;
        this.phoneNumber = phoneNumber;
        this.eMail = eMail;
        this.createTime = createTime;
        this.roleId = roleId;
        this.forbiddenTime = forbiddenTime;
        this.docIds = docIds;
    }

    public SysUser(String username, String studentNum, String major, String college, Integer status, Date createTime) {
        super();
        this.userName = username;
        this.studentNum = studentNum;
        this.major = major;
        this.college = college;
        this.userStatus = status;
        this.createTime = createTime;
        this.roleId = 5;
    }

    public SysUser(String userName, Integer userStatus, Integer roleId, String cnkiName) {
        super();
        this.userName = userName;
        this.userStatus = userStatus;
        this.roleId = roleId;
        this.cnkiName = cnkiName;
    }

    public SysUser(String userName, String passWord, String company, Integer userStatus, Integer uploadAmount,
                   Integer uploadResidue, Integer uploadFail, String phoneNumber, String eMail, Date createTime,
                   Integer roleId, Date forbiddenTime, String cnkiName, Integer docIds, String studentNum, String college,
                   String major, String degree, String realName, byte[] identPhoto, String remarks) {
        super();
        this.userName = userName;
        this.passWord = passWord;
        this.company = company;
        this.userStatus = userStatus;
        this.uploadAmount = uploadAmount;
        this.uploadResidue = uploadResidue;
        this.uploadFail = uploadFail;
        this.phoneNumber = phoneNumber;
        this.eMail = eMail;
        this.createTime = createTime;
        this.roleId = roleId;
        this.forbiddenTime = forbiddenTime;
        this.cnkiName = cnkiName;
        this.docIds = docIds;
        this.studentNum = studentNum;
        this.college = college;
        this.major = major;
        this.degree = degree;
        this.realName = realName;
        this.identPhoto = identPhoto;
        this.remarks = remarks;
    }

    public SysUser(String userName, Integer uploadAmount, Integer uploadResidue, String phoneNumber, String studentNum, String college, String major,
                   String degree, String realName, String sign) {
        super();
        this.userName = userName;
        this.uploadAmount = uploadAmount;
        this.uploadResidue = uploadResidue;
        this.phoneNumber = phoneNumber;
        this.studentNum = studentNum;
        this.college = college;
        this.major = major;
        this.degree = degree;
        this.realName = realName;
        this.sign = sign;
    }

    public SysUser(Integer id, Integer uploadResidue, String sign) {
        super();
        this.id = id;
        this.uploadResidue = uploadResidue;
        this.sign = sign;
    }

}