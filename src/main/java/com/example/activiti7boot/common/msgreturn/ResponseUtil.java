package com.example.activiti7boot.common.msgreturn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 
 * @author ZhiPengyu
 * @ClassName:    [ResponseUtil.java]
 * @Description:  [带状态的相应工具]
 * @CreateDate:   [2020年9月26日 上午10:21:12]
 */
public class ResponseUtil {

	static Logger logger = LoggerFactory.getLogger(ResponseUtil.class);
	
    /**
     * 使用response输出JSON
     * @param response 响应头
     * @param status 状态码
     * @param result 返回内容
     * @throws IOException
     */
    public static void out(HttpServletResponse response,int status, String result) throws IOException{
    	logger.info("调用接口返回："+result);
    	
    	response.setCharacterEncoding("UTF-8");
//    	response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(result);
    }

}
