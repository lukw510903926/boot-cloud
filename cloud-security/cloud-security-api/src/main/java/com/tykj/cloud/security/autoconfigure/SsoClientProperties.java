package com.tykj.cloud.security.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * sso客户端自动配置
 *
 * @author lukw
 * @email 13507615840@163.com
 * @create 2018-07-17 9:39
 **/
@Configuration
@ConfigurationProperties(prefix = "spring.sso")
public class SsoClientProperties implements Serializable {

    private static final long serialVersionUID = -1936998624919386898L;

    private String clientId;

    private String clientKey;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    @Override
    public String toString() {
        return "SsoClientProperties{" +
                "clientId='" + clientId + '\'' +
                ", clientKey='" + clientKey + '\'' +
                '}';
    }
}
