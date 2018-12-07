package com.tykj.cloud.security.util.web;

import java.io.Serializable;
import java.util.*;

/**
 * @author lukw
 * @email 13507615840@163.com
 * @create 2018-08-15 10:02
 **/
public class LoginUser implements Serializable {

    private static final long serialVersionUID = -3326413985204894524L;

    private String username;

    private String name;

    private String token;

    private Set<String> roles = new HashSet<>(0);

    private List<Permission> permissions = new LinkedList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
