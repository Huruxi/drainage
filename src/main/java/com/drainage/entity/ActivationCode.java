package com.drainage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
    private int typeId;
    private int loginState;

    /**
     * 当日在线时长，秒为单位
     */
    private long onlineTimeToday;

    /**
     * 总计在线时长，秒为单位
     */
    private long onlineTimeTotal;

    private Date updateTime;
    private Date addTime;

    @TableField(exist = false)
    private int num;
}
