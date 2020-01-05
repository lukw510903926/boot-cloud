package com.cloud.security.filter;

import com.cloud.common.web.ResponseUtil;
import com.cloud.security.auth.SecurityManager;
import com.cloud.security.util.Constants;
import com.cloud.security.util.PermissionUtil;
import com.cloud.security.util.web.LoginUser;
import com.cloud.security.util.web.Permission;
import com.cloud.security.util.web.SystemPermission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限拦截器 -- 基于servlet
 *
 * @author lukew
 * @eamil 13507615840@163.com
 * @create 2018-08-22 19:41
 **/
public class PermissionFilter implements Filter {

    private SecurityManager securityManager;

    @Autowired
    public void setSecurityManager(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String header = request.getHeader(Constants.AUTH_HEADER);
        if (StringUtils.isBlank(header)) {
            ResponseUtil.forbidden((HttpServletResponse) response);
        }
        LoginUser loginUser = securityManager.readToken(header);
        String requestURI = request.getRequestURI();
        SystemPermission systemPermission = securityManager.getSystemPermission();
        Permission permission = PermissionUtil.matchPermission(requestURI, request.getMethod(), systemPermission.getPermissions());
        if (permission != null) {
            boolean contains = loginUser.getPermissions().contains(permission);
            if (!contains) {
                ResponseUtil.unauthorized((HttpServletResponse) response);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
