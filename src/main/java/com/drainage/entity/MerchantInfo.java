package com.drainage.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author hrd <br/>
 * @date 2020/12/7
 */
@Data
public class MerchantInfo implements java.io.Serializable{
    private static final long serialVersionUID = 7380571062682565751L;

    private int id;
    private String name;
    private String content;

    private Date addTime;
    private Date updateTime;
}
