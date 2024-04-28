
package com.gdb.main.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录时传递的参数
 */
@Data
@Schema(name = "用户登录时传递的数据模型", description = "用户登录时传递的数据模型")
public class UserDTO implements Serializable {

    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

}