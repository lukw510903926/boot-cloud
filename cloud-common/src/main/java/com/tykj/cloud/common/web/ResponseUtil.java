package com.tykj.cloud.common.web;

import java.io.PrintWriter;
import java.io.Serializable;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseUtil {

	private static final String DEFAULT_CONTENT_TYPE = "application/json;charset=UTF-8";

	private static final Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

	public static void writeMsg(HttpServletResponse response, String jsonMsg) {

		try {
			PrintWriter writer = response.getWriter();
			response.setContentType(DEFAULT_CONTENT_TYPE);
			writer.write(jsonMsg);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			logger.error(" response write jsonMsg error : {}", e);
		}
	}

	public static void forbidden(HttpServletResponse response){

		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		ResponseMsg responseMsg = new ResponseMsg(403,"非法请求");
		writeMsg(response, JSONObject.toJSONString(responseMsg));
	}

	public static void unauthorized(HttpServletResponse response){

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		ResponseMsg responseMsg = new ResponseMsg(401,"认证失败");
		writeMsg(response, JSONObject.toJSONString(responseMsg));
	}


}
