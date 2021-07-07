package com.example.activiti7boot.security.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 
 * @author ZhiPengyu
 * @ClassName:    [SecurityCorsFilter.java]
 * @Description:  [跨域过滤]
 * @CreateDate:   [2020年8月13日 下午5:22:46]
 */
@Component
public class SecurityCorsFilter implements Filter {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    
    @Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
    	logger.info("跨域过滤");
		HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("origin"));
        response.setHeader("Access-Control-Allow-Origin","*");  //允许跨域访问的域
//        response.setHeader("Access-Control-Allow-Origin","https://we.cnki.net");
        response.setHeader("Access-Control-Allow-Origin","http://192.168.168.89:8098");  //允许跨域访问的域
        response.setHeader("Access-Control-Allow-Methods","POST,GET,OPTIONS,DELETE,PUT");  //允许使用的请求方法
        response.setHeader("Access-Control-Expose-Headers","Content-Disposition,Authorization,Set-Cookie");
        response.setHeader("Access-Control-Allow-Headers","x-requested-with,Cache-Control,Pragma,Content-Type,Content-Disposition,Authorization,Set-Cookie");  //允许使用的请求方法
        response.setHeader("Access-Control-Allow-Credentials","true");//是否允许请求带有验证信息
        chain.doFilter(request, response);
	}
    
    @Override
    public void destroy() {

    }

}