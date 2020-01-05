package com.cloud.security.util.web;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * 系统资源
 *
 * @author lukew
 * @eamil 13507615840@163.com
 * @create 2018-08-15 20:17
 **/

public class SystemPermission implements Serializable {

    private static final long serialVersionUID = -2261011293320985311L;

    private List<Permission> permissions = new LinkedList<>();

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
