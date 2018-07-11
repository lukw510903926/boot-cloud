package com.tykj.cloud.zuul.route;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * @author lukew
 * @Description:
 * @email 13507615840@163.com
 * @date 2018年6月28日 下午10:10:13
 */
public class DBRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {


    private final static Logger logger = LoggerFactory.getLogger(DBRouteLocator.class);

    private JdbcTemplate jdbcTemplate;

    private ZuulProperties properties;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    public DBRouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.properties = properties;
        logger.info("servletPath:{}", servletPath);
    }


    //父类已经提供了这个方法，这里写出来只是为了说明这一个方法很重要！！！
//    @Override
//    protected void doRefresh() {
//        super.doRefresh();
//    }


    @Override
    public void refresh() {

        doRefresh();
    }

    @Override
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {

        LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap<>();
        //从application.properties中加载路由信息
        routesMap.putAll(super.locateRoutes());
        //从db中加载路由信息
        routesMap.putAll(locateRoutesFromDB());
        //优化一下配置
        LinkedHashMap<String, ZuulProperties.ZuulRoute> values = new LinkedHashMap<>();
        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : routesMap.entrySet()) {

            String path = entry.getKey();
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (StringUtils.hasText(this.properties.getPrefix())) {

                path = this.properties.getPrefix() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            values.put(path, entry.getValue());
        }
        return values;
    }


    private Map<String, ZuulProperties.ZuulRoute> locateRoutesFromDB() {

        Map<String, ZuulProperties.ZuulRoute> routes = new LinkedHashMap<>();

        List<ZuulRouteVO> results = jdbcTemplate.query("select * from gateway_api_define where enabled = true ", new
                BeanPropertyRowMapper<>(ZuulRouteVO.class));

        for (ZuulRouteVO result : results) {

            if (StringUtils.isEmpty(result.getPath())) {
                continue;
            }

            if (StringUtils.isEmpty(result.getServiceId()) && StringUtils.isEmpty(result.getUrl())) {
                continue;
            }

            ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
            try {
                BeanUtils.copyProperties(result, zuulRoute);
            } catch (Exception e) {
                logger.error("=============load zuul route info from db with error==============", e);
            }
            routes.put(zuulRoute.getPath(), zuulRoute);
        }
        return routes;
    }


    public static class ZuulRouteVO {


        /**
         * The ID of the route (the same as its map key by default).
         */

        private String id;


        /**
         * The path (pattern) for the route, e.g. /foo/**.
         */

        private String path;


        /**
         * The service ID (if any) to map to this route. You can specify a physical URL or
         * <p>
         * a service, but not both.
         */

        private String serviceId;


        /**
         * A full physical URL to map to the route. An alternative is to use a service ID
         * <p>
         * and service discovery to find the physical address.
         */

        private String url;


        /**
         * Flag to determine whether the prefix for this route (the path, minus pattern
         * <p>
         * patcher) should be stripped before forwarding.
         */

        private boolean stripPrefix = true;


        /**
         * Flag to indicate that this route should be retryable (if supported). Generally
         * <p>
         * retry requires a service ID and ribbon.
         */

        private Boolean retryable;


        private Boolean enabled;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


        public String getPath() {
            return path;
        }


        public void setPath(String path) {
            this.path = path;
        }


        public String getServiceId() {
            return serviceId;
        }


        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }


        public String getUrl() {
            return url;
        }


        public void setUrl(String url) {
            this.url = url;
        }


        public boolean isStripPrefix() {
            return stripPrefix;
        }


        public void setStripPrefix(boolean stripPrefix) {
            this.stripPrefix = stripPrefix;
        }


        public Boolean getRetryable() {
            return retryable;
        }


        public void setRetryable(Boolean retryable) {
            this.retryable = retryable;
        }


        public Boolean getEnabled() {
            return enabled;
        }


        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }
    }
}