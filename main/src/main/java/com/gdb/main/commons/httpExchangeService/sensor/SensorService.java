package com.gdb.main.commons.httpExchangeService.sensor;

import com.gdb.main.commons.httpExchangeService.pojo.GetSensorData;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;


/**
 * 其中的一个方法就是一个远程服务调用
 * @author Mr.GDB
 */
@HttpExchange("https://api.gizwits.com")
public interface SensorService {
    /**
     * 设置请求的URI和请求方式
     */
    @GetExchange("/app/devdata/{did}/latest")
    GetSensorData getSensorData(@RequestHeader("X-Gizwits-Application-Id") String appID, @PathVariable("did") String did);

}

