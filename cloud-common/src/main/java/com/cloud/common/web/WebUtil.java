package com.cloud.common.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
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

    public static final String LOGIN_USER = "_SESSION_LOGIN_USER";

    public static final String SSO_TOKEN_COOKIE = "_SSO_TOKEN_COOKIE";

    public static LoginUser getLoginUser(HttpServletRequest request) {

        return (LoginUser) getSessionAttribute(request, LOGIN_USER);
    }

    public static LoginUser getLoginUser() {

        return getLoginUser(getRequest());
    }

    /**
     * 获取请求的request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {

        return Optional.ofNullable(getRequestAttributes()).map(ServletRequestAttributes::getRequest).orElse(null);
    }

    /**
     * 获取请求的response
     *
     * @return
     */
    public static HttpServletResponse getResponse() {

        return Optional.ofNullable(getRequestAttributes()).map(ServletRequestAttributes::getResponse).orElse(null);
    }

    private static ServletRequestAttributes getRequestAttributes() {

        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    public static String getLoginUserId() {

        return Optional.ofNullable(getLoginUser()).map(LoginUser::getId).orElse(null);
    }

    public static void setSessionUser(LoginUser loginUser) {
        setSessionAttribute(getRequest(), LOGIN_USER, loginUser);
    }

    public static void setToken(String token) {

        setSessionAttribute(getRequest(), SSO_TOKEN_COOKIE, token);
    }

    public static String getToken() {

        return (String) getSessionAttribute(getRequest(), SSO_TOKEN_COOKIE);
    }

    /**
     * 生成链接的URL
     *
     * @param href
     * @param ctx
     * @return
     */
    public static String getUrl(String href, String ctx) {

        if (!Pattern.matches("^(http://|https://|/).*", href)) {
            href = ctx + '/' + href;
        }
        return href;
    }

    /**
     * 是否Ajax请求
     *
     * @return
     */
    public static boolean isAjaxRequest() {

        String requestWith = (String) Optional.ofNullable(getRequest()).map(request -> request.getAttribute("X-Requested-With")).orElse(null);
        return "XMLHttpRequest".equals(requestWith);
    }

    /**
     * 获取最匹配的url restFul
     *
     * @param request
     * @return
     */
    public static String getBestMatchUrl(HttpServletRequest request) {

        return (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
    }


    /**
     * 获取请求ip
     *
     * @return
     */
    public static String getIp() {

        HttpServletRequest request = getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        } else {
            ip = ip.split(",")[0].trim();
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取请求参数
     *
     * @return
     */
    public static Map<String, Object> getRequestParam() {

        String paramName;
        HttpServletRequest request = getRequest();
        Map<String, Object> map = new HashMap<>();
        Enumeration<String> enumPks = request.getParameterNames();
        while (enumPks.hasMoreElements()) {
            paramName = enumPks.nextElement();
            map.put(paramName, request.getParameter(paramName));
        }
        return map;
    }
}
