package com.gdb.main.service;

import com.gdb.main.pojo.entity.sensor.SensorDataDB;

import java.util.List;
import java.util.Map;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-03-12 15:56
 * @description: 处理 sensor 的业务接口
 **/

public interface SensorDataDBService {
    /**
     * 查询指定编号传感器对应数据库中的最后一条记录，然后通过远程调用获取数据，并且更新后再返回给控制器（更新的数据不插入数据库中）
     * @param name 传感器编号
     * @return 返回指定编号传感器的最新数据
     */
     SensorDataDB queryLatestData(String name);

    /**
     * 查询指定传感器对应的所有数据
     * @param name 传感器编号
     * @param limit 限制的时间范围
     * @return 返回查询到的所有数据
     */
     List<Map<String ,SensorDataDB> > queryAllDataForOneSensor(String name, Double limit);
}
