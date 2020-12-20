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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
    public IPage findActivationCodes(String code,String typeId,String loginState,int offset, int limit) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if(StringUtils.isNotBlank(code)){
            queryWrapper.eq("code",code);
        }

        if(StringUtils.isNotBlank(typeId)){
            queryWrapper.eq("type_id",typeId);
        }
        if(StringUtils.isNotBlank(loginState)){
            queryWrapper.eq("login_state",loginState);
        }

        Page<ActivationCode> page = new Page<>(offset,limit);
        return activationCodeMapper.selectPage(page, queryWrapper);
    }

    @Override
    public List<ActivationCode> findLoginActivationCode() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.ne("login_state",0);
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

    @Override
    public IPage findDailyActiveCodeIncome(int offset, int limit){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("type_id, to_char(add_time, 'yyyy-mm-dd') AS add_time, sum(1) AS num");
        queryWrapper.eq("status",1);
        queryWrapper.groupBy("type_id,to_char(add_time,'yyyy-mm-dd')");
        queryWrapper.orderByDesc("add_time");
        Page<ActivationCode> page = new Page<>(offset,limit);
        return activationCodeMapper.selectPage(page, queryWrapper);
    }

    @Override
    public ActivationCodeLoginLog findActivationCodeLoginLog(String code) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("code",code);
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit 1");
        return codeLoginLogMapper.selectOne(queryWrapper);
    }

    @Override
    public int updateActiveCodeLoginLogUpdateTime(String code) {
        ActivationCodeLoginLog loginLog = findActivationCodeLoginLog(code);
        if(loginLog != null){
            loginLog.setUpdateTime(new Date());
            return codeLoginLogMapper.updateById(loginLog);
        }

        return 0;
    }

    @Override
    public void detectActiveCodeOffline() {
        List<ActivationCode> activationCode = this.findLoginActivationCode();
        long currentTime = System.currentTimeMillis();

        for (ActivationCode code : activationCode) {
            ActivationCodeLoginLog loginLog = this.findActivationCodeLoginLog(code.getCode());
            long time = currentTime - loginLog.getUpdateTime().getTime();
            if(time > 58000){
                this.activeCodeQuitLogin(code);
            }
        }
    }

    @Override
    public void activeCodeQuitLogin(ActivationCode activationCode) {
        Instant instant = activationCode.getUpdateTime().toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();

        if(localDate.equals(LocalDate.now())){
            //当天的情况
            long currentOnlineTime = System.currentTimeMillis() - activationCode.getUpdateTime().getTime();
            activationCode.setOnlineTimeToday(activationCode.getOnlineTimeToday() + (currentOnlineTime/1000));
            activationCode.setOnlineTimeTotal(activationCode.getOnlineTimeTotal() + activationCode.getOnlineTimeToday());
        }else{
            //隔天的情况
            LocalTime time = LocalTime.now();
            long second = time.getHour() * 3600;
            second += time.getMinute() * 60;
            second += time.getSecond();

            activationCode.setOnlineTimeToday(second);
            long currentOnlineTime = System.currentTimeMillis() - activationCode.getUpdateTime().getTime();
            activationCode.setOnlineTimeTotal(activationCode.getOnlineTimeTotal() + (currentOnlineTime/1000));
        }

        activationCode.setLoginState(0);
        activationCode.setUpdateTime(new Date());
        this.updateActiveCode(activationCode);
        this.updateActiveCodeOnlineTime(activationCode.getCode());
    }

}
