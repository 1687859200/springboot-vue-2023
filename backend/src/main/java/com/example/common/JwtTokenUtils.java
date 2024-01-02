package com.example.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.entity.User;
import com.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtTokenUtils {

    private static UserService staticUserService;
    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtils.class);

    @Resource
    private UserService userService;

    @PostConstruct
    public void setUserService() {
        staticUserService = userService;
    }

    /**
     * 生成Token
     */
    public static String getToken(String userId, String password){
        return JWT.create().withAudience(userId)
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) //两个小时后失效
                .sign(Algorithm.HMAC256(password));
    }

    public static User getCurrentUser(){
        String token = null;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            token = request.getHeader("token");
            if(StrUtil.isBlank(token)){
                token = request.getParameter("token");
            }
            if(StrUtil.isBlank(token)){
                log.error("获取当前登录的token失败, token: {}",token);
                return null;
            }
            String userId = JWT.decode(token).getAudience().get(0);
            return staticUserService.findById(Integer.valueOf(userId));
        } catch (Exception e) {
            log.error("获取当前登录的管理员信息失败, token={}",token, e);
            return null;
        }
    }
}
