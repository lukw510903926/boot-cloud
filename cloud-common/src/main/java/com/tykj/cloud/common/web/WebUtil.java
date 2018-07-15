package com.tykj.cloud.common.web;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author : lukew
 * @project : IDEA
 * @createTime : 2018/4/14 8:10
 * @email : 13507615840@163.com
 * @gitHub : https://github.com/lukw510903926
 * @description :
 */
public class WebUtil extends WebUtils {

    private final static ThreadLocal<LoginUser> localUser = new ThreadLocal<>();

    public final static String LOGIN_USER = "_SESSION_LOGIN_USER";

    public final static String SSO_TOKEN_COOKIE = "_SSO_TOKEN_COOKIE";

    public static LoginUser getLoginUser(HttpServletRequest request, HttpServletResponse response) {

        LoginUser loginUser = (LoginUser) request.getSession().getAttribute(LOGIN_USER);
        localUser.set(loginUser);
        return loginUser;
    }

    public static LoginUser getLoginUser() {

        if (localUser.get() == null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            LoginUser loginUser = (LoginUser) request.getSession().getAttribute(LOGIN_USER);
            localUser.set(loginUser);
        }
        return localUser.get();
    }

    /**
     * 获取请求的request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {

        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取请求的response
     *
     * @return
     */
    public static HttpServletResponse getResponse() {

        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static LoginUser getLoginUser(HttpServletRequest request) {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute(LOGIN_USER);
        localUser.set(loginUser);
        return loginUser;
    }

    public static String getLoginUserId() {

        return Optional.ofNullable(getLoginUser()).map(loginUser -> loginUser.getId()).orElse(null);
    }

    public static void setSessionUser(HttpServletRequest request, LoginUser loginUser) {
        localUser.set(loginUser);
        request.getSession().setAttribute(LOGIN_USER, loginUser);
    }

    public static void setToken(HttpServletRequest request, String token) {

        setSessionAttribute(request, SSO_TOKEN_COOKIE, token);
    }

    public static String getToken(HttpServletRequest request) {

        return (String) getSessionAttribute(request, SSO_TOKEN_COOKIE);
    }

    /**
     * 生成链接的URL
     *
     * @param href
     * @param ctx
     * @return
     */
    public static String getUrl(String href, String ctx) {

        String url = href;
        if (!Pattern.matches("^(http://|https://|/).*", url))
            url = ctx + '/' + url;
        return url;
    }

    /**
     * 是否Ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("X-Requested-With");
        return requestedWith != null ? "XMLHttpRequest".equals(requestedWith) : false;
    }
}
