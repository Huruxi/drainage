package com.drainage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.drainage.entity.MerchantAccount;
import com.drainage.entity.MerchantInfo;
import com.drainage.entity.Placard;

import java.util.List;

/**
 * @author hrd <br/>
 * @date 2020/12/8
 */
public interface IMerchantInfoService {

    /**
     * 添加公告
     * @param title
     * @param content
     * @return
     */
    int addPlacard(String title, String content,int isRelease);

    /**
     * 更新公告
     * @param id
     * @param title
     * @param content
     * @return
     */
    int updatePlacard(int id,String title, String content,int isRelease);

    /**
     * 获取公告
     * @param sortType
     * @param pageIndex
     * @param pageSize
     * @return
     */
    IPage findPlacards(int sortType,int pageIndex,int pageSize);

    /**
     * 获取最新发布公告
     * @return
     */
    Placard findNewReleasePlacard();

    /**
     * 获取公告
     * @return
     */
    Placard findPlacard();

    /**
     * 添加商户信息
     * @param name
     * @param val
     * @return
     */
    int addMerchantInfo(String name,String val);

    /**
     * 更新商户信息
     * @param id
     * @param name
     * @param val
     * @return
     */
    int updateMerchantInfo(int id,String name, String val);

    /**
     * 获取商户信息
     * @return
     */
    List<MerchantInfo> findMerchantInfo();


    /**
     * 添加账户信息
     * @param account
     * @return
     */
    int addMerchantAccount(MerchantAccount account);

    /**
     * 更新账户信息
     * @param account
     * @return
     */
    int updateMerchantAccount(MerchantAccount account);

    /**
     * 根据ID获取
     * @param id
     * @return
     */
    MerchantAccount findMerchantAccount(int id);

    /**
     * 删除
     * @param id
     * @return
     */
    int delMerchantAccount(int id);

    /**
     * 获取商户列表
     * @param name
     * @param pageIndex
     * @param pageSize
     * @return
     */
    IPage findMerchantAccounts(String name,int pageIndex,int pageSize);

}
