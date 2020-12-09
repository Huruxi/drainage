package com.drainage.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author hrd <br/>
 * @date 2020/12/7
 */
@Data
public class Placard implements java.io.Serializable {
    private static final long serialVersionUID = -3377915665129278333L;


    private int id;
    private String title;
    private String content;

    private int isRelease;
    private Date addTime;
    private Date updateTime;

}
