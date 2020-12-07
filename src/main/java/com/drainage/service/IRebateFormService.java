package com.drainage.service;

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
    List<RebateForm> findRebateForm(int sortType,int offset,int limit);



}
