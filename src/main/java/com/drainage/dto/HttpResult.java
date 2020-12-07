package com.drainage.dto;

/**
 * @author hrd <br/>
 * @date 2020/12/1
 */
public class HttpResult<T> implements java.io.Serializable {
    private static final long serialVersionUID = -543037053089083556L;

    private int code;
    private String message;
    private T data = null;

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

    public T getData() {
        return data;
    }

    /**
     * 放入响应枚举
     */
    public HttpResult fillCode(CodeEnum codeEnum){
        this.setCode(codeEnum.getCode());
        this.setMessage(codeEnum.getMessage());
        return this;
    }

    /**
     * 放入响应码及信息
     */
    public HttpResult fillCode(int code, String message){
        this.setCode(code);
        this.setMessage(message);
        return this;
    }

    /**
     * 处理成功，放入自定义业务数据集合
     */
    public HttpResult fillData(T data) {
        this.setCode(CodeEnum.SUCCESS.getCode());
        this.setMessage(CodeEnum.SUCCESS.getMessage());
        this.data = data;
        return this;
    }

}
