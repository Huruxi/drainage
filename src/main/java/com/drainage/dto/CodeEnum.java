package com.drainage.dto;

import java.math.BigDecimal;

/**
 * @author hrd <br/>
 * @date 2020/12/1
 */
public enum CodeEnum {

    // 根据业务需求进行添加
    SUCCESS(200,"处理成功"),
    ERROR_PATH(404,"请求地址错误"),
    ERROR_SERVER(505,"服务器内部发生错误"),
    ERROR_PARAMETER(401,"参数错误");

    private int code;
    private String message;

    CodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
