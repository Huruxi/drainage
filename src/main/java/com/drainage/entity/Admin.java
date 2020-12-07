package com.drainage.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author hrd <br/>
 * @date 2020/12/1
 */
@Data
public class Admin implements java.io.Serializable {

    private static final long serialVersionUID = -7179175552854374966L;

    private int id;

    private String name;
    private String password;
    private Date updateTime;
    private Date addTime;
}
