/*
package com.gdb.main.httpExchangeService.controller;

import com.gdb.main.httpExchangeService.SensorService;
import com.gdb.main.httpExchangeService.pojo.GetSensorData;
import com.gdb.main.httpExchangeService.pojo.SensorData;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

*/
/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-03-05 22:05
 * @description: 对远程调用数据的封装
 **//*


@RestController
public class HttpExchangeService {
    @Resource
    private SensorService sensorService;
    @GetMapping("/app/devdata/latest")
    public SensorData getSensorDataLatest() throws Exception {
        */
/*
            1. 首先将文件中的数据读取到Properties中
            2. 然后远程调用获取数据，如果获取到数据，则将数据更新到Properties中
            3. 将Properties写回到文件中。
         *//*

        //将文件中的数据读取到Properties文件中
        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("config/sensor.properties");
        Properties properties = new Properties();
        properties.load(in);

        // 获取远端服务器的数据
        GetSensorData getSensorData = sensorService.getSensorData(properties.getProperty("appID"), properties.getProperty("did"));
        System.out.println(getSensorData);

        //通过getDeclaredFields()方法获取对象类中的所有属性（含私有）
        Field[] fields = getSensorData.getAttr().getClass().getDeclaredFields();

        //遍历属性
        for (Field field : fields) {
            //通过setAccessible()设置为true,允许通过反射访问私有变量
            field.setAccessible(true);

            //获取属性值
            Object value = field.get(getSensorData.getAttr());
            //获取属性名
            String name = field.getName();
            //如果属性不为null则更新数据
            if (value != null) {
                properties.setProperty(name, String.valueOf(value));
            } else
                field.set(getSensorData.getAttr(), Double.parseDouble(properties.getProperty(name)));
        }
        //获取文件的路径

        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath(); //这种方式获取使用打包后的
        System.out.println("=================================");
        System.out.println(path);
        System.out.println("=================================");
        FileOutputStream out = new FileOutputStream(path + "config/sensor.properties");
        properties.store(out, "Sensor Data");
        return getSensorData.getAttr();
    }
}

*/
