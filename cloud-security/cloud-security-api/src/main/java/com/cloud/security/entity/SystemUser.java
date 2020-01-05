package com.cloud.security.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户
 *
 * @author lukew
 * @eamil 13507615840@163.com
 * @create 2018-07-16 19:54
 **/
@Table(name = "t_system_user")
public class SystemUser implements Serializable {

    private static final long serialVersionUID = 2192109518642898359L;

    @Id
    private String id;

    /**
     * 账号
     */
    @Column(name = "username")
    private String username;

    /**
     * 姓名
     */
    @Column(name = "name")
    private String name;

    /**
     * 登录密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 创建时间
     */
    @Column(name = "created")
    private Date created;

    /**
     * 最后登录时间
     */
    @Column(name = "last_login")
    private Date lastLogin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
}
