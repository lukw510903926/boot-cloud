package com.cloud.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 转发认证
 *
 * @author : lukewei
 * @createTime : 2017年11月21日 : 下午2:21:49
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
            log.info("X-Auth-Token ok {}", ctx.getZuulRequestHeaders());
            ctx.addZuulRequestHeader("CLOUD_HEADER", "CLOUD_HEADER_VALUE");
            return true;
        } catch (Exception ex) {
        }
        return true;
    }
}
