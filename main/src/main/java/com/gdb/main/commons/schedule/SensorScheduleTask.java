package com.gdb.main.commons.schedule;


import com.gdb.main.commons.constant.TimeConstant;
import com.gdb.main.commons.httpExchangeService.pojo.GetSensorData;
import com.gdb.main.commons.httpExchangeService.sensor.SensorService;
import com.gdb.main.commons.utils.DateUtil;
import com.gdb.main.mapper.SensorDataDBMapper;
import com.gdb.main.pojo.entity.sensor.SensorDataDB;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
/**
 * @author Mr.Gui
 */
@Component
public class SensorScheduleTask {
    private static final Logger logger = LoggerFactory.getLogger(SensorScheduleTask.class);

    @Resource
    private SensorService sensorService;
    @Resource
    SensorDataDBMapper sensorDataDBMapper;
    @Resource
    private Environment environment;

    // todo 这里只能写数字，不能使用自己设置的常量
    @Scheduled(fixedRate = 30*90*1000)
    public void getSensorData() {
        //这个是需要保存到数据库中的数据对象
        SensorDataDB sensorDataDB = new SensorDataDB();
        //设置当前时刻。
        sensorDataDB.setDate(DateUtil.formatDateTime(new Date()));
        //设置ID
        sensorDataDB.setId(1);
        int count = 0;
        while (true) {
            // 获取远端服务器的数据
            GetSensorData getSensorData = sensorService.getSensorData(environment.getProperty("sensor.appID"), environment.getProperty("sensor.did001"));

            //通过getDeclaredFields()方法获取对象类中的所有属性（含私有）
            Field[] fields = getSensorData.getAttr().getClass().getDeclaredFields();
            //获取返回到数据库中数据的 Class 对象
            Class<? extends SensorDataDB> sensorDataDBClass = sensorDataDB.getClass();
            boolean flag = true;

            //遍历属性
            for (Field field : fields) {
                //通过setAccessible()设置为true,允许通过反射访问私有变量
                field.setAccessible(true);
                try {
                    //获取属性值
                    Double value = (Double) field.get(getSensorData.getAttr());
                    //获取属性名
                    String name = field.getName();
                    //将属性名的首字母大写
                    name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
                    //如果属性不为null则更新数据
                    if (value != null) {
                        //根据属性名获取对象对应的 setXXX 方法。
                        Method setMethod = sensorDataDBClass.getDeclaredMethod("set" + name, Double.class);
                        setMethod.invoke(sensorDataDB, value);
                    } else {
                        Method getMethod = sensorDataDBClass.getDeclaredMethod("get" + name);
                        Object ret = getMethod.invoke(sensorDataDB);
                        if (ret == null) {
                            flag = false;
                        }
                    }
                } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                    throw new RuntimeException();
                }
            }
            count++;
            if(count % 10 ==0) {
                logger.info("服务器第---" + count + "---次尝试连接远程服务器===>  " + sensorDataDB);
            }

            //如果所有的数据都收集齐全了就写入数据库中保存
            if (flag) {
                //将数据的Id设置为null，使用数据库自增的Id
                sensorDataDB.setId(null);
                //写入数据库的代码
                // todo 这里写的是固定的，只获取一个传感器的数据，后面有时间可以修改
                int n  = sensorDataDBMapper.insertOneRow(sensorDataDB, environment.getProperty("sensor.table001"));
                logger.info(new Date() + " ===> 更新数据库数据 " + n + " 条" );
                break;
            }
            try {
                Thread.sleep(TimeConstant.GET_SENSOR_DATA_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
