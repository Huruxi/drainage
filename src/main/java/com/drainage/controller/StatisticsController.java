package com.drainage.controller;

import com.drainage.dto.HttpResult;
import com.drainage.service.IRebateFormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hrd <br/>
 * @date 2020/12/9
 */

@Api(tags = "统计模块")
@RestController
@RequestMapping("/api/statistic")
public class StatisticsController {

    @Autowired
    HttpServletRequest request;

    @Autowired
    private IRebateFormService rebateFormService;

    @ApiOperation("获取激活码每日卖出收入")
    @RequestMapping(value = "/findDailyActiveCodeSellIncome",method = RequestMethod.POST)
    public HttpResult findDailyActiveCodeSellIncome(){

        //
        //select year(addtime) as year,month(addtime) as month, day(addtime) as day, sum(pay_day) as total from t_mp_order_log group by year(addtime),month(addtime),day(addtime);
        //
        return new HttpResult();
    }


    @ApiOperation("获取激活码每日返利收入")
    @RequestMapping(value = "/findDailyActiveCodeRebateIncome",method = RequestMethod.POST)
    public HttpResult findDailyActiveCodeRebateIncome(@RequestParam String code){

        return new HttpResult();
    }

}
