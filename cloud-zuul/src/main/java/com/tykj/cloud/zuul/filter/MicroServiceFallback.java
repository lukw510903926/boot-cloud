package com.tykj.cloud.zuul.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

/**
 * @Description: 针对微服务的回退 服务降级
 * @author lukew
 * @email 13507615840@163.com
 * @date 2018年6月28日 下午9:25:29
 */
public class MicroServiceFallback implements FallbackProvider {

	/**
	 * 微服务标识
	 */
	private String serviceId;

	public MicroServiceFallback(String serviceId) {
		this.serviceId = serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	@Override
	public String getRoute() {

		return this.serviceId;
	}

	@Override
	public ClientHttpResponse fallbackResponse(String route, Throwable cause) {

		return new ClientHttpResponse() {

			@Override
			public HttpStatus getStatusCode() throws IOException {

				return HttpStatus.OK;
			}

			@Override
			public int getRawStatusCode() throws IOException {

				return HttpStatus.OK.value();
			}

			@Override
			public String getStatusText() throws IOException {

				return HttpStatus.OK.toString();
			}

			@Override
			public void close() {

			}

			@Override
			public InputStream getBody() throws IOException {

				return new ByteArrayInputStream(serviceId.getBytes());
			}

			@Override
			public HttpHeaders getHeaders() {

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.TEXT_PLAIN);
				return headers;
			}
		};
	}

}
