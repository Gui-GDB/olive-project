package com.gdb.main.pojo.vo.uservo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-03-31 15:50
 * @description: 返回用户的常用个人信息
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "员工登录返回的数据格式")
public class UserMessage implements Serializable {
    @Schema(description = "主键")
    private Integer id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "性别")
    private Byte sex;

    @Schema(description = "用户编号")
    private String idNumber;

    @Schema(description = "用户账号状态")
    private Byte status;

    @Schema(description = "用户创建时间")
    private String createTime;

    @Schema(description = "用户信息修改时间")
    private String updateTime;

    @Schema(description = "用户创建人")
    private String createUser;

    @Schema(description = "用户修改人")
    private String updateUser;

    @Schema(description = "用户头像")
    private String avatar;
}