package com.tykj.cloud.security.filter;

import com.alibaba.fastjson.JSONObject;
import com.tykj.cloud.api.filter.HeaderFilter;
import com.tykj.cloud.common.web.LoginUser;
import com.tykj.cloud.common.web.RestResult;
import com.tykj.cloud.security.entity.SystemUser;
import com.tykj.cloud.security.feign.LoginFeign;
import com.tykj.cloud.security.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 请求认证
 *
 * @author lukw
 * @email 13507615840@163.com
 * @create 2018-07-17 15:25
 **/
public class AuthenticationFilter implements Filter {

    private LoginFeign loginFeign;

    private Logger logger = LoggerFactory.getLogger(HeaderFilter.class);

    @Autowired
    public void setLoginFeign(LoginFeign loginFeign) {
        this.loginFeign = loginFeign;
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        logger.info("AuthenticationFilter do filter --------------------");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        logger.info("header name : {}", request.getHeaderNames());
        logger.info("cloud-header : {}", request.getHeader(Constants.CLOUD_CLIENT_ID));
        String clientId = request.getHeader(Constants.CLOUD_CLIENT_ID);
        String clientKey = request.getHeader(Constants.CLOUD_CLIENT_KEY);
        String token = request.getHeader(Constants.CLOUD_CLIENT_TOKEN);
        RestResult<LoginUser> loginUser = loginFeign.token(clientId, clientKey, token);
        logger.info("loginUser : {}", JSONObject.toJSONString(loginUser));
        SystemUser systemUser = new SystemUser();
        systemUser.setName(clientId);
        systemUser.setId(clientKey);
        loginFeign.login(systemUser);
        chain.doFilter(servletRequest, response);

    }

    @Override
    public void destroy() {

    }
}
