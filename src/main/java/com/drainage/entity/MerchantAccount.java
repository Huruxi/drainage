package com.drainage.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author hrd <br/>
 * @date 2020/12/14
 */
@Data
public class MerchantAccount implements java.io.Serializable {
    private static final long serialVersionUID = 1370123293854573181L;

    private int id;
    private String code;
    private String name;
    private String mobile;
    private String accountNumber;

    private int payType;
    private int payStatus;

    private BigDecimal money;
    private Date updateTime;
    private Date addTime;
}
