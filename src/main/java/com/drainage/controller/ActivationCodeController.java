package com.drainage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.drainage.dto.CodeEnum;
import com.drainage.dto.HttpResult;
import com.drainage.entity.ActivationCode;
import com.drainage.entity.ActivationCodeLoginLog;
import com.drainage.entity.ActivationCodeType;
import com.drainage.service.IActiveCodeService;
import com.drainage.utils.ActivationCodeGenerator;
import com.drainage.utils.IpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * @author hrd <br/>
 * @date 2020/12/1
 */
@Api(tags = "激活码模块")
@RestController
@RequestMapping("/api/ac")
public class ActivationCodeController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private IActiveCodeService activeCodeService;

//    @Value("login.quantity")
    private int loginQuantity = 20;

    @ApiOperation("获取激活码类型")
    @RequestMapping(value = "/findActivationCodeTypes",method = RequestMethod.POST)
    public HttpResult<ActivationCodeType> findActivationCodeTypes(){
        List<ActivationCodeType> activationCodeType = activeCodeService.findActivationCodeType();
        return new HttpResult<>().fillData(activationCodeType);
    }


    @ApiOperation("生成激活码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "typeId", required = true, value = "分类ID", dataType = "int", paramType = "query"),
    })
    @RequestMapping(value = "/createActiveCode",method = RequestMethod.POST)
    public HttpResult<ActivationCode> createActiveCode(@RequestParam int typeId){
        ActivationCode activationCode = new ActivationCode();
        activationCode.setStatus(0);
        activationCode.setTypeId(typeId);
        activationCode.setUpdateTime(new Date());
        activationCode.setAddTime(new Date());

        activeCodeService.addActiveCode(activationCode);
        if(activationCode.getId() > 0){
            ActivationCodeGenerator generator = new ActivationCodeGenerator();
            String code = generator.generate(String.valueOf(activationCode.getId()), System.currentTimeMillis());
            activationCode.setCode(code);
            activationCode.setUpdateTime(new Date());
            activeCodeService.updateActiveCode(activationCode);
            return new HttpResult<ActivationCode>().fillData(activationCode);
        }

        return new HttpResult().fillCode(CodeEnum.ERROR_SERVER);
    }


    @ApiOperation("获取激活码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sortType", required = true, value = "根据Id排序方式,0 正序 1 倒序", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "offset", required = true, value = "数据偏移量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", required = true, value = "限制获取数据大小", dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "/findActiveCodes",method = RequestMethod.POST)
    public HttpResult<IPage> findActiveCodes(@RequestParam int sortType,
                                                            @RequestParam int offset,
                                                            @RequestParam int limit){
        IPage page = activeCodeService.batchFindActiveCode(sortType, offset, limit);
        return new HttpResult<>().fillData(page);
    }


    @ApiOperation("搜索激活码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", required = true, value = "激活码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "typeId", required = true, value = "激活码类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "loginState", required = true, value = "登录状态", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "offset", required = true, value = "数据偏移量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", required = true, value = "限制获取数据大小", dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "/searchActiveCodes",method = RequestMethod.POST)
    public HttpResult<IPage> searchActiveCodes(@RequestParam String code,
                                                        @RequestParam String typeId,
                                                        @RequestParam String loginState,
                                                        @RequestParam int offset,
                                                        @RequestParam int limit){
        IPage page = activeCodeService.findActivationCodes(code,typeId,loginState,offset,limit);
        return new HttpResult<>().fillData(page);
    }


    @ApiOperation("删除激活码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "激活码ID", dataType = "int", paramType = "query"),
    })
    @RequestMapping(value = "/delActiveCode",method = RequestMethod.POST)
    public HttpResult delActiveCode(@RequestParam int id){
        activeCodeService.delActiveCode(id);
        return new HttpResult().fillCode(CodeEnum.SUCCESS);
    }

    @ApiOperation("激活码登录")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "code", required = true, value = "激活码", dataType = "String", paramType = "query"),
    })
    @RequestMapping(value = "/0/activeCodeLogin",method = RequestMethod.POST)
    public HttpResult activeCodeLogin(@RequestParam String code){
        ActivationCode activationCode = activeCodeService.findActivationCode(code);
        if(activationCode == null){
            return new HttpResult().fillCode(CodeEnum.ERROR_PARAMETER);
        }

        String ipAddress = IpUtil.getIpAddrByRequest(request);
        if(ipAddress == null){
            return new HttpResult().fillCode(500,"异常登录");
        }else{
            int codeLoginQuantity = activeCodeService.currentIpActiveCodeLoginQuantity(ipAddress);
            if(codeLoginQuantity >= loginQuantity){
                return new HttpResult().fillCode(500,"当前登录机器不能超过" + loginQuantity + "个激活码");
            }
        }

        if(activationCode.getLoginState() == 1){
            return new HttpResult().fillCode(500,"当前激活码已登录,不能重复登录");
        }

        //计算当日在线时长
        ActivationCodeLoginLog loginLog = activeCodeService.findActivationCodeLoginLog(code);
        if(loginLog != null){
            Instant instant = loginLog.getAddTime().toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDate localDate = instant.atZone(zoneId).toLocalDate();

            if(!localDate.equals(LocalDate.now())){
                activationCode.setOnlineTimeToday(0);
            }
        }

        activationCode.setStatus(1);
        activationCode.setLoginState(1);
        activationCode.setUpdateTime(new Date());
        int result = activeCodeService.updateActiveCode(activationCode);
        if(result == 1){
            ActivationCodeLoginLog codeLoginLog = new ActivationCodeLoginLog();
            codeLoginLog.setCode(activationCode.getCode());
            codeLoginLog.setIp(ipAddress);
            codeLoginLog.setUpdateTime(new Date());
            codeLoginLog.setAddTime(new Date());
            activeCodeService.addActivationCodeLoginLog(codeLoginLog);
            return new HttpResult().fillData(activationCode);
        }
        return new HttpResult().fillCode(CodeEnum.ERROR_SERVER);
    }

    @ApiOperation("获取单个激活码信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", required = true, value = "激活码", dataType = "String", paramType = "query"),
    })
    @RequestMapping(value = "/0/findActiveCode",method = RequestMethod.POST)
    public HttpResult findActiveCode(@RequestParam String code){
        ActivationCode activationCode = activeCodeService.findActivationCode(code);
        if(activationCode == null){
            return new HttpResult().fillCode(CodeEnum.ERROR_PARAMETER);
        }

        return new HttpResult().fillData(activationCode);
    }

    @ApiOperation("激活码退出登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", required = true, value = "激活码", dataType = "String", paramType = "query"),
    })
    @RequestMapping(value = "/0/activeCodeQuit",method = RequestMethod.POST)
    public HttpResult activeCodeQuit(@RequestParam String code){
        ActivationCode activationCode = activeCodeService.findActivationCode(code);
        if(activationCode == null){
            return new HttpResult().fillCode(CodeEnum.ERROR_PARAMETER);
        }

        activeCodeService.activeCodeQuitLogin(activationCode);
        return new HttpResult().fillCode(CodeEnum.SUCCESS);
    }

    @ApiOperation("检测激活码登录状态,超过分钟内未检测,则视为已下线")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", required = true, value = "激活码", dataType = "String", paramType = "query"),
    })
    @RequestMapping(value = "/0/detectLoginState",method = RequestMethod.POST)
    public HttpResult detectLoginState(@RequestParam String code){
        ActivationCode activationCode = activeCodeService.findActivationCode(code);
        if(activationCode == null){
            return new HttpResult().fillCode(CodeEnum.ERROR_PARAMETER);
        }

        if(activationCode.getLoginState() == 0){
            return new HttpResult().fillCode(500,"激活码登录已下线");
        }

        int result = activeCodeService.updateActiveCodeLoginLogUpdateTime(code);
        if(result > 0){
            return new HttpResult().fillCode(CodeEnum.SUCCESS);
        }

        return new HttpResult().fillCode(CodeEnum.ERROR_SERVER);
    }


}
