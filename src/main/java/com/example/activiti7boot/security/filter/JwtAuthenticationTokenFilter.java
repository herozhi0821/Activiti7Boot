package com.example.activiti7boot.security.filter;

import com.example.activiti7boot.common.msgreturn.ResponseUtil;
import com.example.activiti7boot.common.msgreturn.ResultCode;
import com.example.activiti7boot.common.msgreturn.ResultGenerator;
import com.example.activiti7boot.security.jwt.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 
 * @author ZhiPengyu
 * @ClassName:    [JwtAuthenticationTokenFilter.java]
 * @Description:  [访问过滤-用于访问校验jwt过滤,并将用户角色信息写入内存]
 * @CreateDate:   [2020年8月13日 下午4:32:37]
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    ResultGenerator resultGenerator;
    @Autowired
    PathMatcher pathMatcher;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    
    @Value("${jwt.token.header}")
    private String token_header;
    @Value("${jwt.token.type}")
    private String token_type;
    @Value("${jwt.token.passUrl}")
    private List<String> passUrl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) 
    		throws ServletException, IOException {
    	
    	String requestUrl = request.getRequestURI();
    	logger.info("["+requestUrl+"]访问校验jwt,并将用户角色信息写入内存!");
    	//判断URL是否需要验证
        Boolean flag = true;
        for(String url : passUrl){
            if(pathMatcher.match(url, requestUrl)){
                flag = false;
                break;
            }
        }
        
        //根据判断结果执行校验
        if (flag) {//需要校验的校验后才通过
        	String authHeader = request.getHeader(this.token_header);
            if (authHeader != null && authHeader.startsWith(this.token_type)) {
            	//获取token
                String authToken = authHeader.substring(this.token_type.length());
                if (!jwtTokenUtil.isTokenExpired(authToken)) {//无效token去更新
                	//根据token获取用户名
                    String username = jwtTokenUtil.getUserNameFromToken(authToken);
                    if (username != null ) {//&& SecurityContextHolder.getContext().getAuthentication() == null
                    	UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                        if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                            //验证token是否有效
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                            
                            chain.doFilter(request, response);
                            return;
                        }else {
                        	logger.error("访问url:["+requestUrl+"]校验失败，需更新token!");
                            ResponseUtil.out(response, 5002, resultGenerator.getFreeResult(ResultCode.TOKEN_TO_REFRESH).toString());
                            return;
    					}
                    }
				}
            }else {
            	logger.error("访问url:["+requestUrl+"]时Token："+authHeader);
			}
		}else {//无需校验直接通过
			chain.doFilter(request, response);
            return;
		}
        logger.error("访问url:["+requestUrl+"]校验失败，无权访问!");
        ResponseUtil.out(response, 403, resultGenerator.getFreeResult(ResultCode.NO_PERMISSION).toString());
    }
    
}
