package com.drainage.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author hrd <br/>
 * @date 2020/12/7
 */
@Data
public class ActivationCodeType implements java.io.Serializable {
    private static final long serialVersionUID = 833747362095522402L;

    private int id;

    private String name;

    private Date updateTime;
    private Date addTime;


}
