package com.drainage.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author hrd <br/>
 * @date 2020/12/7
 */
@Data
public class ActivationCodeLoginLog implements java.io.Serializable {
    private static final long serialVersionUID = -7868306363654033740L;

    private int id;
    /**
     * 激活码
     */
    private String code;
    /**
     * 在线时间以秒为单位
     */
    private int onlineTime;

    /**
     * 登录IP
     */
    private String ip;

    private Date updateTime;
    private Date addTime;
}
