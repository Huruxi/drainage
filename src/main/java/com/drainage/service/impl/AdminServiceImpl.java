package com.drainage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.drainage.entity.Admin;
import com.drainage.mapper.IAdminMapper;
import com.drainage.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author hrd <br/>
 * @date 2020/12/2
 */
@Service
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private IAdminMapper adminMapper;

    @Override
    public int addAdmin(Admin admin) {
        if(admin == null){
            return 0;
        }

        admin.setAddTime(new Date());
        admin.setUpdateTime(new Date());

        Admin admin1 = findAdmin(admin.getName());
        if(admin1 != null){
            return 0;
        }

        return adminMapper.insert(admin);
    }

    @Override
    public Admin findAdmin(String name) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name",name);
        queryWrapper.last("limit 1");
        return adminMapper.selectOne(queryWrapper);
    }

    @Override
    public List<Admin> findAdmins() {
        return adminMapper.selectList(null);
    }
}
