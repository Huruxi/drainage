package com.drainage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.drainage.dto.HttpResult;
import com.drainage.service.IActiveCodeService;
import com.drainage.service.IRebateFormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

    @Autowired
    private IActiveCodeService activeCodeService;

    @ApiOperation("获取激活码每日卖出收入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", required = true, value = "页码", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", required = true, value = "页面数据大小", dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "/findDailyActiveCodeSellIncome",method = RequestMethod.POST)
    public HttpResult findDailyActiveCodeSellIncome(@RequestParam int pageIndex,
                                                    @RequestParam int pageSize){
        IPage activeCodeIncome = activeCodeService.findDailyActiveCodeIncome(pageIndex, pageSize);
        return new HttpResult().fillData(activeCodeIncome);
    }


    @ApiOperation("获取激活码每日返利收入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", required = true, value = "激活码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageIndex", required = true, value = "页码", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", required = true, value = "页面数据大小", dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "/findDailyActiveCodeRebateIncome",method = RequestMethod.POST)
    public HttpResult<IPage> findDailyActiveCodeRebateIncome(@RequestParam String code,
                                                      @RequestParam int pageIndex,
                                                      @RequestParam int pageSize){
        //select code, year(add_time) as year,month(add_time) as month, day(add_time) as day, sum(money) as total from T_REBATE_FORM  group by year(add_time),month(add_time),day(add_time)
        IPage rebateIncome = rebateFormService.findDailyActiveCodeRebateIncome(code, pageIndex, pageSize);
        return new HttpResult().fillData(rebateIncome);
    }

}
