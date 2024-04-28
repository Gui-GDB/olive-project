package com.gdb.main.pojo.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String username;

    private String name;

    private String password;

    private String phone;

    private Byte sex;

    private String idNumber;

    private Byte status;

    private String createTime;

    private String updateTime;

    private Integer createUser;

    private Integer updateUser;

    private String avatar;

}
