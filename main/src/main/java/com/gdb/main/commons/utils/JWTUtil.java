package com.gdb.main.commons.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.Map;

/**
 * @author Mr.Gui
 */
public class JWTUtil {
    /**
     * 生成token
     * @param payload token携带的信息
     * @return token字符串
     */
    public static String getToken(Long ttlMillis, String secretKey, Map<String,String> payload) {
        //指定过期时间
        Date date = new Date(System.currentTimeMillis() + ttlMillis);

        JWTCreator.Builder builder = JWT.create();
        // 构建payload
        payload.forEach(builder::withClaim);
        // 指定过期时间和签名算法
        return builder.withExpiresAt(date).sign(Algorithm.HMAC256(secretKey));
    }


    /**
     * 解析token
     * @param token token字符串
     * @return 解析后的token
     */
    public static DecodedJWT decodeToken(String token, String secretKey) {
        // 创建解析对象，使用的算法和secret要与创建token时保持一致
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
        // 解析指定的token
        return jwtVerifier.verify(token);
    }
}
