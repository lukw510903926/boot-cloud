package com.cloud.common.http;

import org.apache.commons.collections4.MapUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Map;

/**
 * @author : lukew
 * @project : IDEA
 * @createTime : 2018/4/13 23:29
 * @email : 13507615840@163.com
 * @gitHub : https://github.com/lukw510903926
 * @description :
 */
public class WebClientUtil {

    private static final Duration TIME_OUT = Duration.ofMillis(15);

    private static final MediaType MEDIA_TYPE = MediaType.APPLICATION_JSON_UTF8;

    private static final MediaType MULTIPART_FORM = MediaType.MULTIPART_FORM_DATA;

    /**
     * @param parameter  请求参数
     * @param url        请求路径
     * @param resultType 返回结果类型
     * @return
     */
    public static <V> V post(Object parameter, String url, Class<V> resultType) {

        return post(uri(url, HttpMethod.POST), parameter, MEDIA_TYPE, resultType);
    }

    /**
     * @param parameter  请求参数
     * @param url        请求路径
     * @param header     请求头
     * @param resultType 返回结果类型
     * @return
     */
    public static <V> V post(Object parameter, String url, Map<String, String> header, Class<V> resultType) {

        RequestBodySpec uri = uri(url, HttpMethod.POST);
        header(uri, header);
        return post(uri, parameter, MEDIA_TYPE, resultType);
    }

    public static <V> V postMultipart(Map<String, Object> parameter, String url, InputStream inputStream,
                                      String fileName, Class<V> resultType) throws IOException {

        MultipartFile multipartFile = new MockMultipartFile(fileName, inputStream);
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("jarFile", multipartFile);
        if (MapUtils.isNotEmpty(parameter)) {
            parameter.forEach(params::add);
        }
        return post(uri(url, HttpMethod.POST), params, MULTIPART_FORM, resultType);
    }

    private static <V> V post(RequestBodySpec uri, Object parameter, MediaType mediaType, Class<V> resultType) {

        return uri.contentType(mediaType).body(Mono.justOrEmpty(parameter), Object.class).retrieve()
                .bodyToMono(resultType).blockOptional(TIME_OUT).orElse(null);
    }


    public static <V> V get(String url, Class<V> resultType) {

        return uri(url, HttpMethod.GET).retrieve().bodyToMono(resultType).blockOptional(TIME_OUT).orElse(null);
    }

    public static <V> V get(String url, Map<String, String> header, Class<V> resultType) {

        RequestBodySpec uri = uri(url, HttpMethod.GET);
        header(uri, header);
        return uri.retrieve().bodyToMono(resultType).blockOptional(TIME_OUT).orElse(null);
    }

    private static RequestBodySpec uri(String url, HttpMethod method) {

        return WebClient.create().method(method).uri(url);
    }

    private static void header(RequestBodySpec uri, Map<String, String> header) {

        if (MapUtils.isNotEmpty(header)) {
            header.forEach(uri::header);
        }
    }
}
