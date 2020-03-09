package com.anningtex.useokhttpandrx.bean;

import java.util.List;

import lombok.Data;

/**
 * @Author Song
 * @Desc: {"code":1,"msg":"登录成功","data":[]}
 */

@Data
public class LoginBean {
    /**
     * code : 1
     * msg : 登录成功
     * data : []
     */

    private int code;
    private String msg;
    private List<?> data;
}
