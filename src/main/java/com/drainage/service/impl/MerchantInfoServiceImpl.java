package com.drainage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drainage.entity.MerchantInfo;
import com.drainage.entity.Placard;
import com.drainage.mapper.IMerchantInfoMapper;
import com.drainage.mapper.IPlacardMapper;
import com.drainage.service.IMerchantInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author hrd <br/>
 * @date 2020/12/8
 */
@Service
public class MerchantInfoServiceImpl implements IMerchantInfoService {

    @Autowired
    private IMerchantInfoMapper merchantInfoMapper;

    @Autowired
    private IPlacardMapper placardMapper;


    @Override
    public int addPlacard(String title, String content,int isRelease) {
        Placard placard = new Placard();
        placard.setTitle(title);
        placard.setContent(content);
        placard.setIsRelease(isRelease);
        placard.setAddTime(new Date());
        placard.setUpdateTime(new Date());
        return placardMapper.insert(placard);
    }

    @Override
    public int updatePlacard(int id, String title, String content,int isRelease) {
        Placard placard = placardMapper.selectById(id);
        if(placard != null){
            placard.setTitle(title);
            placard.setContent(content);
            placard.setIsRelease(isRelease);
            placard.setUpdateTime(new Date());
            return placardMapper.updateById(placard);
        }
        return 0;
    }

    @Override
    public IPage findPlacards(int sortType, int pageIndex, int pageSize) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if(sortType == 0){
            queryWrapper.orderByAsc("id");
        }else {
            queryWrapper.orderByDesc("id");
        }

        Page<Placard> page = new Page<>(pageIndex,pageSize);
        return placardMapper.selectPage(page,queryWrapper);
    }

    @Override
    public Placard findNewReleasePlacard() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("is_release",1);
        queryWrapper.orderByAsc("id");
        queryWrapper.last("limit 1");

        return placardMapper.selectOne(queryWrapper);
    }

    @Override
    public Placard findPlacard() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByAsc("id");
        queryWrapper.last("limit 1");
        return placardMapper.selectOne(queryWrapper);
    }

    @Override
    public int addMerchantInfo(String name, String val) {
        MerchantInfo merchantInfo = new MerchantInfo();
        merchantInfo.setName(name);
        merchantInfo.setContent(val);
        merchantInfo.setAddTime(new Date());
        merchantInfo.setUpdateTime(new Date());
        return merchantInfoMapper.insert(merchantInfo);
    }

    @Override
    public int updateMerchantInfo(int id,String name, String val){
        MerchantInfo merchantInfo = merchantInfoMapper.selectById(id);
        if(merchantInfo != null){
            merchantInfo.setName(name);
            merchantInfo.setContent(val);
            merchantInfo.setUpdateTime(new Date());
            return merchantInfoMapper.updateById(merchantInfo);
        }
        return 0;
    }

    @Override
    public MerchantInfo findMerchantInfo() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByAsc("id");
        queryWrapper.last("limit 1");
        return merchantInfoMapper.selectOne(queryWrapper);
    }
}
