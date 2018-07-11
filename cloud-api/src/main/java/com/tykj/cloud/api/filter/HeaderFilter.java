package com.tykj.cloud.api.filter;

import com.tykj.cloud.api.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 请求头拦截
 *
 * @author lukw
 * @email 13507615840@163.com
 * @create 2018-07-11 17:00
 **/
public class HeaderFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(HeaderFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        logger.info("header name : {}",request.getHeaderNames());
        logger.info("cloud-header : {}", request.getHeader(Constants.CLOUD_HEADER));
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
