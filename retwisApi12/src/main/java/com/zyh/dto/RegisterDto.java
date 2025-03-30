package com.zyh.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
/**
 * 注册时接收数据的封装
 * 校验数据是否符合规则
 */
@Data
public class RegisterDto implements Serializable {

    @Size(min = 3,max = 20)
    @NotBlank(message = "昵称不能为空")
    private String username;

    @Size(min = 6,max = 15)
    @NotBlank(message = "密码不能为空")
    private String password;

    @Size(min=6,max=15)
    @NotBlank
    private String  secondPassword;


    @NotBlank
    @Email
    private String email;

    @Size(min=6,max=6)
    @NotBlank
    private String code;
}
