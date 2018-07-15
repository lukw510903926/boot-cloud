package com.tykj.cloud.common.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseUtil {

	private static final String DEFAULT_CONTENT_TYPE = "application/json;charset=UTF-8";

	private static final Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

	public static void writeMsg(HttpServletResponse response, String msg) {

		try {
			PrintWriter writer = response.getWriter();
			response.setContentType(DEFAULT_CONTENT_TYPE);
			writer.write(msg);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			logger.error(" response write msg error : {}", e);
		}
	}
}
