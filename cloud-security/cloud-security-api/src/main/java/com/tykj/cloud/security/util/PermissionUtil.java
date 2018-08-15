package com.tykj.cloud.security.util;

import com.tykj.cloud.security.util.web.Permission;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.List;

/**
 * 资源匹配工具类
 *
 * @author lukw
 * @email 13507615840@163.com
 * @create 2018-08-15 10:08
 **/
public class PermissionUtil {

    private static PathMatcher pathMatcher = new AntPathMatcher();

    /**
     * @param requestUrl  请求路径
     * @param method      请求类型
     * @param permissions 拥有的资源点
     * @return
     */
    public static Permission matchPermission(String requestUrl, String method, List<Permission> permissions) {

        Permission permission = null;
        for (int i = 0; i < permissions.size(); i++) {
            permission = permissions.get(i);
            boolean match = pathMatcher.match(permission.getUrl(), requestUrl);
            if (match && permission.getMethodType().equalsIgnoreCase(method)) {
                break;
            }
        }
        return permission;
    }
}
