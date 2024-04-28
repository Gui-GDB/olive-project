package com.gdb.main.commons.utils;

/**
 * 经纬度 GPS ---> 高德工具类
 */
public class GPSTransToAMapUtil {

    /**
     * 椭球参数
     */
    private static final double pi = 3.14159265358979324;

    /**
     * 经纬度 GPS转高德
     *
     * @param wgLon GPS经度
     * @param wgLat GPS维度
     * @return 转化后的经纬度坐标
     */
    public static AMap transform(double wgLon, double wgLat) {
        if (outOfChina(wgLat, wgLon)) {
            return new AMap(wgLon, wgLat);
        }

        double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
        double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
        double radLat = wgLat / 180.0 * pi;
        double magic = Math.sin(radLat);
        //椭球的偏心率
        double ee = 0.00669342162296594323;
        //卫星椭球坐标投影到平面地图坐标系的投影因子
        double a = 6378245.0;
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double transLat = wgLat + dLat;
        double transLon = wgLon + dLon;
        return new AMap(transLon, transLat);
    }

    /**
     * 判断是否为国外坐标，，不在国内不做偏移
     *
     * @param lat 维度
     * @param lon 经度
     * @return 在国外返回 true，否则返回 false
     */
    private static Boolean outOfChina(double lat, double lon) {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        return lat < 0.8293 || lat > 55.8271;
    }

    /**
     * 纬度转换
     */
    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret = getRet(x, ret, y * pi, y / 3.0);
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 经度转换
     */
    private static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret = getRet(x, ret, x * pi, x / 3.0);
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
        return ret;
    }

    private static double getRet(double x, double ret, double v, double v2) {
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(v) + 40.0 * Math.sin(v2 * pi)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 高德经纬度类
     *
     * @param longitude 经度
     * @param latitude  维度
     */
        public record AMap(double longitude, double latitude) {
    }
}
