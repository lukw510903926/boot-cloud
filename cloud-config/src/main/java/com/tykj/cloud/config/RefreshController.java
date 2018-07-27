package com.tykj.cloud.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.endpoint.RefreshBusEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO 2.0 自定义实现配置刷新 ? 1.x endpoint实现
 *
 * @author lukw
 * @email 13507615840@163.com
 * @create 2018-07-27 16:31
 **/
@RestController
@RequestMapping("/bus")
public class RefreshController {

    private Logger logger = LoggerFactory.getLogger(RefreshController.class);

    @Autowired
    private RefreshBusEndpoint refreshBusEndpoint;

    @GetMapping("refresh")
    public String refresh() {

        try {
            this.refreshBusEndpoint.busRefresh();
            return "success";
        } catch (Exception e) {
            logger.error(" config refresh fail : {}",e);
            return "false :"+e.getStackTrace();
        }

    }
}
