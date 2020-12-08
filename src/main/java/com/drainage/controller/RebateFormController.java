package com.drainage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.drainage.dto.CodeEnum;
import com.drainage.dto.HttpResult;
import com.drainage.entity.ActivationCode;
import com.drainage.entity.RebateForm;
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
import java.util.List;

/**
 * @author hrd <br/>
 * @date 2020/12/1
 */
@Api(tags = "返利模块")
@RestController
@RequestMapping("/api/rebate")
public class RebateFormController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private IRebateFormService rebateFormService;

    @Autowired
    private IActiveCodeService activeCodeService;


    @ApiOperation("检索激活码返利记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", required = true, value = "激活码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sortType", required = true, value = "根据Id排序方式,0 正序 1 倒序", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "offset", required = true, value = "数据偏移量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", required = true, value = "限制获取数据大小", dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "/searchRebateForm",method = RequestMethod.POST)
    public HttpResult<List<RebateForm>> searchRebateForm(@RequestParam String code,
                                                         @RequestParam int sortType,
                                                         @RequestParam int offset,
                                                         @RequestParam int limit){

        ActivationCode activationCode = activeCodeService.findActivationCode(code);
        if(activationCode == null){
            return new HttpResult<>().fillCode(CodeEnum.ERROR_PARAMETER);
        }

        IPage rebateForms = rebateFormService.findActiveCodeRebateForm(code,sortType, offset, limit);
        return new HttpResult<>().fillData(rebateForms);
    }

    @ApiOperation("获取返利记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sortType", required = true, value = "根据Id排序方式,0 正序 1 倒序", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "offset", required = true, value = "数据偏移量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", required = true, value = "限制获取数据大小", dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "/findRebateForm",method = RequestMethod.POST)
    public HttpResult<IPage> findRebateForm(@RequestParam int sortType,
                                                       @RequestParam int offset,
                                                       @RequestParam int limit){

        IPage rebateForms = rebateFormService.findRebateForm(sortType, offset, limit);
        return new HttpResult<>().fillData(rebateForms);
    }

}
