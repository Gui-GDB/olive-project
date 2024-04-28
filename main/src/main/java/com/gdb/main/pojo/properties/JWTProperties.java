package com.gdb.main.pojo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 用户登录生成jwt的相关配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "olive.jwt")
public class JWTProperties {
    /**
     * 设置jwt签名加密时使用的秘钥
     */
    private String secretKey;
    /**
     * 设置jwt过期时间
     */
    private Long ttlMillis;
    /**
     * 设置前端传递过来的令牌名称
     */
    private String tokenName;
}
