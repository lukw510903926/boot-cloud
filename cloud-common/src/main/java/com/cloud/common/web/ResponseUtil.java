package com.cloud.common.web;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

public class ResponseUtil {

    private static final String DEFAULT_CONTENT_TYPE = "application/json;charset=UTF-8";

    private static final Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

    public static void writeMsg(HttpServletResponse response, String jsonMsg) {

        try (OutputStream writer = response.getOutputStream()) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType(DEFAULT_CONTENT_TYPE);
            response.setHeader("content-type", "text/html;UTF-8");
            response.setCharacterEncoding("UTF-8");
            writer.write(jsonMsg.getBytes());
            writer.flush();
        } catch (Exception e) {
            logger.error(" response write msg error : {}", e);
        }
    }

    public static void forbidden(HttpServletResponse response) {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ResponseMsg responseMsg = new ResponseMsg(403, "非法请求");
        writeMsg(response, JSONObject.toJSONString(responseMsg));
    }

    public static void unauthorized(HttpServletResponse response) {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ResponseMsg responseMsg = new ResponseMsg(401, "认证失败");
        writeMsg(response, JSONObject.toJSONString(responseMsg));
    }


}
