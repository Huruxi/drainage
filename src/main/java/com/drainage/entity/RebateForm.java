package com.drainage.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author hrd <br/>
 * @date 2020/12/1
 */
@Data
public class RebateForm implements java.io.Serializable{
    private static final long serialVersionUID = -1116269256796302740L;

    private int id;

    private String code;
    private BigDecimal money;

    private Date updateTime;
    private Date addTime;
}
