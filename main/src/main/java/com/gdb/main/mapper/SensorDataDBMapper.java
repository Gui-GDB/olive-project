package com.gdb.main.mapper;

import com.gdb.main.pojo.entity.sensor.SensorDataDB;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 处理 sensor 数据的Dao层接口
 * @author Mr.Gui
 */
public interface SensorDataDBMapper {
    /**
     * 向指定编号传感器对应的数据库表中插入一条记录
     *
     * @param sensorDataDB 需要传入的数据
     * @param name         传感器编号
     * @return 返回插入影响的记录条数
     */
    @Insert("insert into sensor${name}" +
            " values "  +
            " (#{sensorDataDB.id}, #{sensorDataDB.hPa}, #{sensorDataDB.smoke}, #{sensorDataDB.temperature}, #{sensorDataDB.AQI}, #{sensorDataDB.humidness}, #{sensorDataDB.lux}, #{sensorDataDB.CO}, #{sensorDataDB.date})")
    int insertOneRow(@Param("sensorDataDB") SensorDataDB sensorDataDB, @Param("name") String name);

    /**
     * 查询指定编号传感器对应的数据库中的最后一条记录
     *
     * @param name 传感器编号
     * @return 数据库中最后一条记录映射的对象
     */
    @Select("select * from sensor${name} order by id desc limit 1")
    SensorDataDB selectLatestData(String name);

    /**
     * 查询指定编号传感器对应的数据库中的所有数据
     * @param limit 限制的记录条数
     * @param name 传感器编号
     * @return 返回所有的查询结果
     */
    @Select("select * from sensor${name} order by id desc limit #{limit}")
    List<Map<String, SensorDataDB>> selectAllDataForOneSensor(String name, Integer limit);
}
