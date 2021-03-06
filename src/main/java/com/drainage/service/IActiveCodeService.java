package com.drainage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.drainage.entity.ActivationCode;
import com.drainage.entity.ActivationCodeLoginLog;
import com.drainage.entity.ActivationCodeType;

import java.util.List;

/**
 * @author hrd <br/>
 * @date 2020/12/3
 */
public interface IActiveCodeService {

    /**
     * 获取激活码类型
     * @return
     */
    List<ActivationCodeType> findActivationCodeType();

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
     * 获取激活码
     * @param code
     * @return
     */
    IPage findActivationCodes(String code,String typeId,String loginState,int offset, int limit);

    /**
     * 获取登录激活码
     * @return
     */
    List<ActivationCode> findLoginActivationCode();

    /**
     * 批量获取激活码
     * @param sortType
     * @param offset
     * @param limit
     * @return
     */
    IPage batchFindActiveCode(int sortType, int offset, int limit);


    /**
     * 添加登录日志
     * @param codeLoginLog
     * @return
     */
    int addActivationCodeLoginLog(ActivationCodeLoginLog codeLoginLog);

    /**
     * 更新激活码登录时长
     * @param code
     * @return
     */
    int updateActiveCodeOnlineTime(String code);

    /**
     * 当前IP激活码登录数量
     * @param ip
     * @return
     */
    int currentIpActiveCodeLoginQuantity(String ip);

    /**
     * 获取激活码激活返利
     * @param offset
     * @param limit
     * @return
     */
    IPage findDailyActiveCodeIncome(int offset, int limit);

    /**
     * 获取激活码最新登录日志
     * @param code
     * @return
     */
    ActivationCodeLoginLog findActivationCodeLoginLog(String code);

    /**
     * 更新激活码登录日志的更新时间
     * @return
     */
    int updateActiveCodeLoginLogUpdateTime(String code);

    /**
     * 检测激活码是否离线
     */
    void detectActiveCodeOffline();

    /**
     * 激活码退出登录
     * @param activationCode
     */
    void activeCodeQuitLogin(ActivationCode activationCode);
}
