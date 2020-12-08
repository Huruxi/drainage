package com.drainage.controller;

import com.drainage.service.IMerchantInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hrd <br/>
 * @date 2020/12/8
 */
@Api(tags = "商家模块")
@RestController
@RequestMapping("/api/merchant")
public class MerchantInfoController {


    @Autowired
    private HttpServletRequest request;

    @Autowired
    private IMerchantInfoService merchantInfoService;








}
