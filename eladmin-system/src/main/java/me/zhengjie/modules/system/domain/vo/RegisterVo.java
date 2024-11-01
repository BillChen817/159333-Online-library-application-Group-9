package me.zhengjie.modules.system.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @BelongsProject: eladmin
 * @CreateTime: 2024/9/1 9:37
 * @Author: Zhaoyang Chen
 * @Description: 登录信息
 */
@Data
public class RegisterVo implements Serializable {
    private String username;
    private String nickName;
    private String email;
    private String phone;
    private String password;
}
