package com.drainage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drainage.entity.ActivationCode;
import com.drainage.entity.ActivationCodeType;
import com.drainage.entity.RebateForm;
import com.drainage.mapper.IActivationCodeMapper;
import com.drainage.mapper.IActivationCodeTypeMapper;
import com.drainage.mapper.IRebateFormMapper;
import com.drainage.service.IActiveCodeService;
import com.drainage.service.IRebateFormService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author hrd <br/>
 * @date 2020/12/3
 */
@Service
public class RebateFormServiceImpl implements IRebateFormService {

    @Autowired
    private IRebateFormMapper rebateFormMapper;

    @Autowired
    private IActiveCodeService activeCodeService;

    @Autowired
    private IActivationCodeMapper activationCodeMapper;

    @Autowired
    private IActivationCodeTypeMapper codeTypeMapper;

//    @Value("login.duration")
    private int duration = 240;

    @Override
    public int addRebateForm(RebateForm rebateForm) {
        return rebateFormMapper.insert(rebateForm);
    }

    @Override
    public IPage findRebateForm(int sortType, int offset, int limit) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if(sortType == 0){
            queryWrapper.orderByAsc("id");
        }else {
            queryWrapper.orderByDesc("id");
        }

        Page<RebateForm> page = new Page<>(offset,limit);
        return rebateFormMapper.selectPage(page, queryWrapper);
    }

    @Override
    public IPage findActiveCodeRebateForm(String code, int sortType, int offset, int limit) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("code",code);
        if(sortType == 0){
            queryWrapper.orderByAsc("id");
        }else {
            queryWrapper.orderByDesc("id");
        }

        Page<RebateForm> page = new Page<>(offset,limit);
        return rebateFormMapper.selectPage(page, queryWrapper);
    }

    /**
     * 获取今日的激活码返利
     * @param code
     * @return
     */
    public List<RebateForm> findToDayCodeRebateForm(String code){
        String startTime = DateFormatUtils.format(new Date(),"yyyy-MM-dd 00:00:00");
        String endTime = DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss");

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("code",code);
        queryWrapper.between("add_time",startTime,endTime);
        return rebateFormMapper.selectList(queryWrapper);
    }

    @Override
    public void loginRebate() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("login_state",2);
        List<ActivationCode> activationCodes = activationCodeMapper.selectList(queryWrapper);
        List<ActivationCodeType> activationCodeTypes = codeTypeMapper.selectList(null);

        if(activationCodes == null || activationCodes.size() == 0
            || activationCodeTypes == null || activationCodeTypes.size() == 0){
            return;
        }

        long currentTime = System.currentTimeMillis();
        for (ActivationCode activationCode : activationCodes) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(activationCode.getAddTime().getTime());
            calendar.add(Calendar.MONTH, 1);

            if (currentTime < calendar.getTimeInMillis()) {

                for (ActivationCodeType codeType : activationCodeTypes) {

                    if (activationCode.getTypeId() == codeType.getId()) {
                        List<RebateForm> toDayCodeRebateForm = findToDayCodeRebateForm(activationCode.getCode());
                        Rebate rebate = new Rebate();
                        BigDecimal rebateMoney = BigDecimal.ZERO;

                        if (toDayCodeRebateForm != null && toDayCodeRebateForm.size() > 0) {//已有返利的情况
                            if (toDayCodeRebateForm.size() >= duration) {
                                //激活码退出登录
//                                activationCode.setLoginState(0);
//                                activationCode.setUpdateTime(new Date());
//                                activeCodeService.updateActiveCode(activationCode);
//                                activeCodeService.updateActiveCodeOnlineTime(activationCode.getCode());
                                break;
                            }

                            BigDecimal amountReturned = toDayCodeRebateForm.stream().map(rebateForm -> rebateForm.getMoney()).reduce(BigDecimal.ZERO, BigDecimal::add);
                            BigDecimal balance = codeType.getMoney().subtract(amountReturned);
                            rebate.remainMoney = balance;
                            rebate.remainSize = (duration - toDayCodeRebateForm.size());
                            rebateMoney = getRandomMoney(rebate);

                        } else {//今日首次返利
                            rebate.remainMoney = codeType.getMoney();
                            rebate.remainSize = duration;
                            rebateMoney = getRandomMoney(rebate);
                        }

                        RebateForm codeBalance = findActiveCodeBalance(activationCode.getCode(), null, null);
                        RebateForm rebateForm = new RebateForm();
                        rebateForm.setCode(activationCode.getCode());
                        rebateForm.setMoney(rebateMoney);
                        rebateForm.setType(0);
                        rebateForm.setBalance(codeBalance != null ? codeBalance.getBalance().add(rebateMoney) : rebateMoney);
                        rebateForm.setUpdateTime(new Date());
                        rebateForm.setAddTime(new Date());
                        addRebateForm(rebateForm);
                        break;
                    }

                }
            }
        }
    }

    @Override
    public IPage findDailyActiveCodeRebateIncome(String code,int offset, int limit) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("code, to_char(add_time,'yyyy-mm-dd') as add_time,sum(money) as money");
        if(StringUtils.isNotBlank(code)){
            queryWrapper.eq("code",code);
        }
        queryWrapper.groupBy("code,to_char(add_time,'yyyy-mm-dd')");
        queryWrapper.orderByDesc("add_time");
        Page<RebateForm> page = new Page<>(offset,limit);
        return rebateFormMapper.selectPage(page, queryWrapper);
    }

    @Override
    public IPage statisticsActiveCodeRebate(String code, String startTime, String endTime, int offset, int limit) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("code,sum(money) as money");
        if(StringUtils.isNotBlank(code)){
            queryWrapper.eq("code",code);
        }

        if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
            queryWrapper.between("add_time",startTime,endTime);
        }

        queryWrapper.groupBy("code");
        Page<RebateForm> page = new Page<>(offset,limit);
        return rebateFormMapper.selectPage(page, queryWrapper);
    }

    @Override
    public RebateForm findActiveCodeBalance(String code, String startTime, String endTime) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("code",code);
        if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
            queryWrapper.select("code,sum(money) as money");
            queryWrapper.eq("type",0);
            queryWrapper.between("add_time",startTime,endTime);
        }else{
            queryWrapper.orderByDesc("id");
            queryWrapper.last("limit 1");
        }

        return rebateFormMapper.selectOne(queryWrapper);
    }


    public static BigDecimal getRandomMoney(Rebate _rebate) {
        // remainSize 剩余的返利数量
        // remainMoney 剩余的钱
        if (_rebate.remainSize == 1) {
            _rebate.remainSize--;
            return _rebate.remainMoney.setScale(2, BigDecimal.ROUND_DOWN);
        }

        BigDecimal random = BigDecimal.valueOf(Math.random());
        BigDecimal min   = BigDecimal.valueOf(0.01);

        BigDecimal halfRemainSize = BigDecimal.valueOf(_rebate.remainSize).divide(new BigDecimal(2), BigDecimal.ROUND_UP);
        BigDecimal max1 = _rebate.remainMoney.divide(halfRemainSize, BigDecimal.ROUND_DOWN);
        BigDecimal minRemainAmount = min.multiply(BigDecimal.valueOf(_rebate.remainSize - 1)).setScale(2, BigDecimal.ROUND_DOWN);
        BigDecimal max2 = _rebate.remainMoney.subtract(minRemainAmount);
        BigDecimal max = (max1.compareTo(max2) < 0) ? max1 : max2;

        BigDecimal money = random.multiply(max).setScale(2, BigDecimal.ROUND_DOWN);
        money = money.compareTo(min) < 0 ? min: money;

        _rebate.remainSize--;
        _rebate.remainMoney = _rebate.remainMoney.subtract(money).setScale(2, BigDecimal.ROUND_DOWN);;
        return money;
    }

    static class Rebate {
        //剩余的返利数量
        int    remainSize;
        //剩余的钱
        BigDecimal remainMoney;
    }
}
