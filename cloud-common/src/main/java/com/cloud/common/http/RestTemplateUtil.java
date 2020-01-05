package com.cloud.common.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.MapUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * restTemplate请求
 *
 * @author lukw
 * @email 13507615840@163.com
 * @create 2018-08-15 15:15
 **/
public class RestTemplateUtil {

    private static RestTemplate restTemplate = new RestTemplate();

    static {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(10000);
        restTemplate.setRequestFactory(requestFactory);
    }

    public static <T> T get(String url, Class<T> resultType) {

        return getRequest(url, null, resultType);
    }

    public static <T> T get(String url, Class<T> resultType, Map<String, String> header) {

        return getRequest(url, header, resultType);
    }

    public static <T> T post(String url, Map<String, Object> params, Class<T> resultType) {

        return postRequest(url, params, null, resultType);
    }

    public static <T> T post(String url, Map<String, Object> params, Map<String, String> header, Class<T> resultType) {

        return postRequest(url, params, header, resultType);
    }

    private static <T> T getRequest(String url, Map<String, String> header, Class<T> resultType) {

        HttpEntity<String> httpEntity = httpEntity(null, header);
        ResponseEntity<T> exchange = restTemplate.exchange(url, HttpMethod.GET, httpEntity, resultType);
        return exchange.getBody();
    }

    private static <T> T postRequest(String url, Map<String, Object> params, Map<String, String> header, Class<T> resultType) {

        HttpEntity<String> httpEntity = httpEntity(params, header);
        return restTemplate.postForObject(url, httpEntity, resultType);
    }

    private static HttpEntity<String> httpEntity(Map<String, Object> params, Map<String, String> header) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8.toString());
        if (MapUtils.isNotEmpty(header)) {
            header.forEach(headers::add);
        }
        return new HttpEntity<>(JSONObject.toJSONString(params), headers);
    }
}
