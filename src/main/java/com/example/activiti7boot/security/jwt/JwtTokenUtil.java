package com.example.activiti7boot.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    
    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.accessExpiration}")
    private long accessExpiration;
    @Value("${jwt.token.refreshExpiration}")
    private long refreshExpiration;
    
    
    //-------------------------生成部分--------------------------
    //根据负责生成JWT的token
    private String generateAccessToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateAccessExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    //生成token的过期时间
    private Date generateAccessExpirationDate() {
        return new Date(System.currentTimeMillis() + accessExpiration);
    }
    
  //-------------------------使用部分--------------------------
  //从token中获取JWT中的负载
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            LOGGER.error("JWT格式验证失败:{}", token);
        }
        return claims;
    }
    
    /**
     * 从token中获取登录用户名
     */
    public String getUserNameFromToken(String token) {
        String username = null;
        try {
            Claims claims = getClaimsFromToken(token);
            if (claims != null) {
            	username = claims.getSubject();	
			}
        } catch (Exception e) {
        	LOGGER.error("从token中获取登录用户名失败:{}", e.toString());
        }
        return username;
    }

    /**
     * 判断token是否已经失效
     * @param token
     * @return 过期为true
     */
    public boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        if (expiredDate == null) {
			return true;
		}
        return expiredDate.before(new Date());
    }

    //从token中获取过期时间
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
			return null;
		}
        return claims.getExpiration();
    }

    /**
     * 根据用户信息生成access_token
     */
    public String getAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateAccessToken(claims);
    }

    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     * @param userDetails 从数据库中查询出来的用户信息
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
    
    
    /**
     * 判断refresh_token是否有效
     * @param refresh_token
     * @return 有效为true
     */
    public boolean canRefresh(String refresh_token) {
        return !isTokenExpired(refresh_token);
    }

    /**
     * 刷新token
     */
    public String refreshToken(String refresh_token) {
        Claims claims = getClaimsFromToken(refresh_token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateAccessToken(claims);
    }
}