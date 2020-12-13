package com.drainage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.drainage.entity.RebateForm;

import java.util.List;

/**
 * @author hrd <br/>
 * @date 2020/12/3
 */
public interface IRebateFormService {

    /**
     * 添加返利记录
     * @param rebateForm
     * @return
     */
    int addRebateForm(RebateForm rebateForm);


    /**
     * 获取返利记录
     * @param sortType
     * @param offset
     * @param limit
     * @return
     */
    IPage findRebateForm(int sortType, int offset, int limit);

    /**
     * 获取返利记录
     * @param sortType
     * @param offset
     * @param limit
     * @return
     */
    IPage findActiveCodeRebateForm(String code,int sortType,int offset,int limit);

    /**
     * 登录返利
     */
    void loginRebate();

    /**
     * 获取激活码每日返利收入
     * @param code
     * @return
     */
    IPage findDailyActiveCodeRebateIncome(String code,int offset, int limit);

}
