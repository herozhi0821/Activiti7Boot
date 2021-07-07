package com.example.activiti7boot.common.msgreturn;

/**
 * 
 * 1001～1999 区间表示参数错误
 * 2001～2999 区间表示用户错误
 * 3001～3999 区间表示接口异常
 * 4001～4999 区间表示论文错误
 * @Description: 响应码枚举，参考HTTP状态码的语义
 * @author ZhiPengyu
 * @date: 2020年4月29日 上午9:27:40
 */
public enum ResultCode {
    /* 成功 */
    SUCCESS("200", "操作成功!"),
    /* 失败 */
    FAIL("400", "操作失败!"),
    FAIL_SERVER("500", "服务器异常!"),
    FAIL_TOKEN("5001", "Token有误!"),
    TOKEN_TO_REFRESH("5002", "请更新令牌！"),
    
	/* 参考HTTP状态码 */
    LOGIN_RE("405", "请重新登录!"),
    NO_PERMISSION("403", "没有权限!"),
    LOGIN_NO("402", "未登录!"),
    LOGIN_FAIL("401", "登录失败!"),
    LOGIN_SUCCESS("200", "登录成功!"),
    LOGOUT_SUCCESS("200", "退出登录!"),
    
    ORDER_NOT_FINISH("2001", "订单支付失败，正在处理，或未支付！"),
    
    SESSION_EXPIRES("101", "会话到期!"),
    SESSION_EXPIRES_OTHER_LOGIN("101", "会话到期!其他用户已登录！"),
    
    
    
    /* 参数错误：1000～1999 */
    PARAM_NOT_VALID("1001", "参数无效"),
    PARAM_IS_BLANK("1002", "参数为空"),
    PARAM_TYPE_ERROR("1003", "参数类型错误"),
    PARAM_NOT_COMPLETE("1004", "参数缺失"),
    PARAM_FILE_TYPE_ERROR("1005", "文件类型错误"),
    
    /* 接口错误：1000～1999 */
    API_EXCEPTION("3001", "接口异常"),
    
    
    DOCUMENT_NOT_FOUND("3002", "文献id不存在或订单有误！"),
    USER_NOT_FOUND("3003", "用户不存在！"),
    API_REPEAT_VISIT_60S("3004", "接口60秒重复操作"),
    
    /* 用户错误 */
    USER_NOT_LOGIN("2001", "用户未登录"),
    USER_CREDENTIALS_ERROR("2002", "密码错误"),
    USER_ACCOUNT_LOCKED("2003", "用户已被禁用！"),
    USER_ACCOUNT_NOT_EXIST("2004", "手机号未注册！"),
    USER_ACCOUNT_ALREADY_EXIST("2005", "用户已注册！"),
    USER_PASSWORD_NO_SAME("2006", "您输入密码不一致！"),
    USER_PASSWORD_NO_FORMAT("2007", "密码格式不符！"),
	USER_NAME_TOOLONG("2008", "用户名超过20字符！"),
	USER_PASSWORD_NOTWO("2009", "请输入两次密码！"),
	USER_REGISTER_NOT_ALLOWEED("2010", "该用户不允许注册！"),
	USER_DATA_EXCEPTION("2011", "该用户数据异常！"),
	
	/* 论文错误 */
	DOCUMENT_TOTAL_NUM_NO("4001", "用户已无可上传篇数！"),
	DOCUMENT_FAIL_NUM_UP("4002", "失败篇数超过上限!"),
	DOCUMENT_IMG_SIZE_UP("4003", "图片文件不得大于10MB！"),
	DOCUMENT_CON_SIZE_UP("4004", "论文文件不得大于100MB！"),
	DOCUMENT_CON_FORMAT_NO("4005", "论文文件格式不符！"),
	DOCUMENT_IMG_FORMAT_NO("4006", "图片文件格式不符！支持jpg、png"),
	DOCUMENT_NO_OR_PAY("4007", "文件不存在或未支付！");
    
    private String code;
    private String message;
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	/**
	 * 
	 * @param code
	 * @param message
	 */
    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
