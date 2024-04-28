package com.gdb.main.commons.httpExchangeService;

import com.gdb.main.commons.httpExchangeService.sensor.SensorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-03-05 19:07
 * @description: 创建HTTP服务代理对象，并且交给Spring容器管理
 **/
@Configuration(proxyBeanMethods = false)
public class HttpExchangeConfiguration {
    /**
     * 生成 传感器数据 远程调用的对象
     */
    @Bean
    public SensorService requestService() {
        WebClient webClient = WebClient.builder().build();
        return HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient)).build().createClient(SensorService.class);
    }
}
