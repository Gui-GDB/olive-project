package com.gdb.main.pojo.vo.uservo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Mr.Gui
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "员工登录返回的数据格式")
public class UserLoginVO implements Serializable {

    @Schema(description = "主键值")
    private Integer id;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "jwt令牌")
    private String token;

    @Schema(description = "token过期时间")
    private String tokenOverdueDate;

}