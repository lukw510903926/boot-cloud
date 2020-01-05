package com.cloud.zuul.filter;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PROXY_KEY;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.REQUEST_URI_KEY;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVICE_ID_KEY;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * @author lukew
 * @Description:
 * @email 13507615840@163.com
 * @date 2018年6月28日 下午10:10:13
 */
@Component
public class PermissionFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(PermissionFilter.class);

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String uri = request.getRequestURI();
        String contextURI = (String) context.get(REQUEST_URI_KEY);
        if (contextURI != null) {
            try {
                uri = UriUtils.encodePath(contextURI, characterEncoding(request));
            } catch (Exception e) {
                logger.debug("unable to encode uri path from context, falling back to uri from request", e);
            }
        }
        logger.info(" routeId filter routeId : {},serviceId : {}", context.get(PROXY_KEY), context.get(SERVICE_ID_KEY));
        logger.info(" zuul filter url : {}", uri);
        return null;
    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 1;
    }

    private String characterEncoding(HttpServletRequest request) {
        return request.getCharacterEncoding() != null ? request.getCharacterEncoding()
                : WebUtils.DEFAULT_CHARACTER_ENCODING;
    }
}
