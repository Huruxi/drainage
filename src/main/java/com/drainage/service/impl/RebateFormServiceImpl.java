package com.drainage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.drainage.entity.RebateForm;
import com.drainage.mapper.IRebateFormMapper;
import com.drainage.service.IRebateFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hrd <br/>
 * @date 2020/12/3
 */
@Service
public class RebateFormServiceImpl implements IRebateFormService {

    @Autowired
    private IRebateFormMapper rebateFormMapper;

    @Override
    public int addRebateForm(RebateForm rebateForm) {
        return rebateFormMapper.insert(rebateForm);
    }

    @Override
    public List<RebateForm> findRebateForm(int sortType, int offset, int limit) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if(sortType == 0){
            queryWrapper.orderByAsc("id");
        }else {
            queryWrapper.orderByDesc("id");
        }
        queryWrapper.last("limit " + offset + "," + limit);
        return rebateFormMapper.selectList(queryWrapper);
    }
}
