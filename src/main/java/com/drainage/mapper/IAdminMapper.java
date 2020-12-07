package com.drainage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.drainage.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author hrd <br/>
 * @date 2020/12/1
 */
@Mapper
public interface IAdminMapper extends BaseMapper<Admin> {

    /**
     * 添加管理
     * @param admin
     * @return
     */
    int insertAdmin(Admin admin);

    /**
     * 获取所有管理员
     * @return
     */
    List<Admin> findAdmins();
}
