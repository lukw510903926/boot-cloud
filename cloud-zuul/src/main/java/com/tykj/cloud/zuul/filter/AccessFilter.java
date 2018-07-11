package com.tykj.cloud.zuul.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 转发认证
 * 
 * @project : tykj-zuul
 * @createTime : 2017年11月21日 : 下午2:21:49
 * @author : lukewei
 * @description :
 */
@Component
public class AccessFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {

		return true;
	}

	@Override
	public Object run() {

		RequestContext ctx = RequestContext.getCurrentContext();
		try {
			log.info("X-Auth-Token ok {}",ctx.getZuulRequestHeaders());
			ctx.addZuulRequestHeader("CLOUD_HEADER","CLOUD_HEADER_VALUE");
			return true;
		} catch (Exception ex) {
		}
		return true;
	}
}
