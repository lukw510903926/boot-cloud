package com.cloud.api.filter;

import com.cloud.api.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.cloud.api.util.Constants.CLOUD_RESPONSE_HEADER;

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
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        logger.info("header name : {}", request.getHeaderNames());
        logger.info("cloud-header : {}", request.getHeader(Constants.CLOUD_HEADER));
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.addHeader(CLOUD_RESPONSE_HEADER, CLOUD_RESPONSE_HEADER + "_value");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
