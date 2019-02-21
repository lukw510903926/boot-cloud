
#### **spring-cloud 集成hystrix**

1:依赖spring-cloud-starter-netflix-hystrix spring-cloud-starter-netflix-hystrix-dashboard 模块 启动类添加注解
EnableHystrixDashboard和EnableCircuitBreaker

2:配置断点actuator/hystrix.stream可以访问management.endpoints.web.exposure.include='*'

3:如果是restTemplate访问接口的方式需要在rest接口上添加@HystrixCommand注解并配置 fallbackMethod方法

4:如果是Feign方式发布接口需要在@FeignClient 配置fallback属性,目标类为一个class 并实现当前的Feign接口
  在消费者端将此类发布为一个spring组件

5:通过消费者访问生产者,获取数据 在消费者端口下访问/hystrix 到达Dashboard,在第一个input框中输入生产者 端口下的 
/actuator/hystrix.stream 即:domain:port/actuator/hystrix.stream  到达监控页面

注意: 必须先通过消费者访问生成者 才可以在监控页面获取到数据