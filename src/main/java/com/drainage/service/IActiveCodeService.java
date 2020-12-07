package com.drainage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.drainage.entity.ActivationCode;

import java.util.List;

/**
 * @author hrd <br/>
 * @date 2020/12/3
 */
public interface IActiveCodeService {

    /**
     * 添加激活码
     * @param activeCode
     * @return
     */
    int addActiveCode(ActivationCode activeCode);

    /**
     * 更新激活码
     * @param activeCode
     * @return
     */
    int updateActiveCode(ActivationCode activeCode);

    /**
     * 删除激活码
     * @param id
     * @return
     */
    int delActiveCode(int id);

    /**
     * 获取激活码
     * @param id
     * @return
     */
    ActivationCode findActivationCode(int id);

    /**
     * 获取激活码
     * @param code
     * @return
     */
    ActivationCode findActivationCode(String code);

    /**
     * 批量获取激活码
     * @param sortType
     * @param offset
     * @param limit
     * @return
     */
    IPage batchFindActiveCode(int sortType, int offset, int limit);

}
