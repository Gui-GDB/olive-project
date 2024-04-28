package com.gdb.main.service.impl;

import com.gdb.main.commons.httpExchangeService.pojo.GetSensorData;
import com.gdb.main.commons.httpExchangeService.sensor.SensorService;
import com.gdb.main.commons.utils.DateUtil;
import com.gdb.main.mapper.SensorDataDBMapper;
import com.gdb.main.pojo.entity.sensor.SensorDataDB;
import com.gdb.main.service.SensorDataDBService;
import jakarta.annotation.Resource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-03-12 16:02
 * @description: 传感器数据库业务接口的实现类
 **/

@Service
public class SensorDataDBServiceImpl implements SensorDataDBService {

    @Resource
    private SensorDataDBMapper sensorDataDBMapper;
    @Resource
    private SensorService sensorService;
    @Resource
    private Environment environment;

    @Override
    public SensorDataDB queryLatestData(String name) {
        //调用指定传感器的远程调用接口，获取当前最新的数据
        GetSensorData.SensorData sensorData = sensorService.getSensorData(environment.getProperty("sensor.appID"), environment.getProperty("sensor.did" + name)).getAttr();
        //获取指定传感器当前数据库中最新的数据，然后进行比对，如果新获取的数据不为空，则更新后才返回给控制器
        SensorDataDB sensorDataDB = sensorDataDBMapper.selectLatestData(name);
        //设置时间为当前的时间
        sensorDataDB.setDate(DateUtil.formatDateTime(new Date()));

        //获取sensorData的所有的属性
        Field[] declaredFields = sensorData.getClass().getDeclaredFields();
        //遍历所有的属性
        for (Field field : declaredFields) {
            //通过setAccessible()设置为true,允许通过反射访问私有变量
            field.setAccessible(true);
            try {
                //获取属性名和属性的值
                String name1 = field.getName();
                Double value = (Double) field.get(sensorData);
                //判断值是否为空
                if (value != null) {
                    name1 = "set" + Character.toUpperCase(name1.charAt(0)) + name1.substring(1);
                    //获取SensorDataDB对应的set方法
                    Method declaredMethod = sensorDataDB.getClass().getDeclaredMethod(name1, Double.class);
                    //更新值
                    declaredMethod.invoke(sensorDataDB, value);
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        return sensorDataDB;
    }

    @Override
    public List<Map<String, SensorDataDB>> queryAllDataForOneSensor(String name, Double limit) {
        //换算成多长时间的数据
        limit = (limit * 24);
        //转化为整数
        int count = limit.intValue();
        return sensorDataDBMapper.selectAllDataForOneSensor(name, count);
    }
}

