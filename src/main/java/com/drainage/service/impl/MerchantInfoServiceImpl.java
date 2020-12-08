package com.drainage.service.impl;

import com.drainage.mapper.IMerchantInfoMapper;
import com.drainage.mapper.IPlacardMapper;
import com.drainage.service.IMerchantInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
