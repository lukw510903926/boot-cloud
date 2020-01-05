package com.cloud.security.util.web;

import java.io.Serializable;

/**
 * 资源点
 *
 * @author lukw
 * @email 13507615840@163.com
 * @create 2018-08-15 10:04
 **/
public class Permission implements Serializable {

    private static final long serialVersionUID = 253200029685524113L;

    /**
     * 资源描述
     */
    private String name;

    /**
     * url 路径 rest 路径录入为 /resource/{username}/age
     */
    private String url;

    /**
     * post delete get put
     */
    private String methodType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }
}
