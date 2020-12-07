package com.drainage.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author hrd <br/>
 * @date 2020/12/1
 */
@Data
public class ActivationCode implements java.io.Serializable {
    private static final long serialVersionUID = -3179675927391414133L;

    private int id;

    private String code;
    private int status;
    private Date updateTime;
    private Date addTime;
}
