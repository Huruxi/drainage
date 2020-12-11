package com.drainage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drainage.entity.ActivationCode;
import com.drainage.entity.ActivationCodeLoginLog;
import com.drainage.entity.ActivationCodeType;
import com.drainage.mapper.IActivationCodeLoginLogMapper;
import com.drainage.mapper.IActivationCodeMapper;
import com.drainage.mapper.IActivationCodeTypeMapper;
import com.drainage.service.IActiveCodeService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author hrd <br/>
 * @date 2020/12/3
 */
@Service
public class ActiveCodeServiceImpl implements IActiveCodeService {

    @Autowired
    private IActivationCodeMapper activationCodeMapper;

    @Autowired
    private IActivationCodeLoginLogMapper codeLoginLogMapper;

    @Autowired
    private IActivationCodeTypeMapper codeTypeMapper;

    @Override
    public List<ActivationCodeType> findActivationCodeType() {
        return codeTypeMapper.selectList(null);
    }

    @Override
    public int addActiveCode(ActivationCode activeCode) {
        if(activeCode == null){
            return 0;
        }

        ActivationCode activationCode = findActivationCode(activeCode.getCode());
        if(activationCode != null){
            return 0;
        }
        return activationCodeMapper.insert(activeCode);
    }

    @Override
    public int updateActiveCode(ActivationCode activeCode) {
        return activationCodeMapper.updateById(activeCode);
    }

    @Override
    public int delActiveCode(int id) {
        return activationCodeMapper.deleteById(id);
    }

    @Override
    public ActivationCode findActivationCode(int id) {
        return activationCodeMapper.selectById(id);
    }

    @Override
    public ActivationCode findActivationCode(String code) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("code",code);
        queryWrapper.last("limit 1");
        return activationCodeMapper.selectOne(queryWrapper);
    }

    @Override
    public List<ActivationCode> findLoginActivationCode() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("login_state",1);

        return activationCodeMapper.selectList(queryWrapper);
    }

    @Override
    public IPage batchFindActiveCode(int sortType, int offset, int limit) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if(sortType == 0){
            queryWrapper.orderByAsc("id");
        }else {
            queryWrapper.orderByDesc("id");
        }

        Page<ActivationCode> page = new Page<>(offset,limit);
        return activationCodeMapper.selectPage(page, queryWrapper);
    }

    @Override
    public int addActivationCodeLoginLog(ActivationCodeLoginLog codeLoginLog) {
        return codeLoginLogMapper.insert(codeLoginLog);
    }

    @Override
    public int updateActiveCodeOnlineTime(String code) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("code",code);
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit 1");
        ActivationCodeLoginLog codeLoginLog = codeLoginLogMapper.selectOne(queryWrapper);
        if(codeLoginLog != null){
            long time = System.currentTimeMillis() - codeLoginLog.getAddTime().getTime();
            codeLoginLog.setOnlineTime((int)time/(1000 * 60));
            codeLoginLog.setLoginState(0);
            codeLoginLog.setUpdateTime(new Date());
            return codeLoginLogMapper.updateById(codeLoginLog);
        }

        return 0;
    }

    @Override
    public int currentIpActiveCodeLoginQuantity(String ip) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("ip",ip);
        queryWrapper.eq("login_state",1);
        Integer count = codeLoginLogMapper.selectCount(queryWrapper);
        return count == null ? 0 : count;

    }
}
