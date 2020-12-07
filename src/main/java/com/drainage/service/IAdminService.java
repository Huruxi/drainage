package com.drainage.service;

import com.drainage.entity.Admin;

import java.util.List;

/**
 * @author hrd <br/>
 * @date 2020/12/2
 */
public interface IAdminService {

    /**
     * 添加管理员
     * @param admin
     * @return
     */
    int addAdmin(Admin admin);

    /**
     * 获取管理员
     * @param name
     * @return
     */
    Admin findAdmin(String name);

    /**
     * 获取所有管理员
     * @return
     */
    List<Admin> findAdmins();
}
