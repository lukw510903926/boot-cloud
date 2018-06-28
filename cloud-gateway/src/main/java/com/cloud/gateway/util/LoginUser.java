package com.cloud.gateway.util;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lukw
 * @email 13507615840@163.com
 * @create 2018-06-28 14:43
 **/
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 5643320199094298517L;

    private String id;

    private String name;

    private String username;

    private String token;

    private List<String> permission = new LinkedList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getPermission() {
        return permission;
    }

    public void setPermission(List<String> permission) {
        this.permission = permission;
    }
}
