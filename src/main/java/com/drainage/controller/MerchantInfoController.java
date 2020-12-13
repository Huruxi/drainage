package com.drainage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.drainage.dto.CodeEnum;
import com.drainage.dto.HttpResult;
import com.drainage.entity.MerchantInfo;
import com.drainage.entity.Placard;
import com.drainage.service.IMerchantInfoService;
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


    @ApiOperation("添加公告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isRelease", required = true, value = "是否发布", dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "title", required = true, value = "公告标题", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "content", required = true, value = "公告内容", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/addPlacard",method = RequestMethod.POST)
    public HttpResult addPlacard(@RequestParam boolean isRelease,
                                 @RequestParam String title,
                                 @RequestParam String content){
        int releaseStatus = isRelease ? 1 : 0;
        int result = merchantInfoService.addPlacard(title, content,releaseStatus);
        if(result > 0){
            return new HttpResult().fillCode(CodeEnum.SUCCESS);
        }

        return new HttpResult().fillCode(CodeEnum.ERROR_SERVER);
    }

    @ApiOperation("更新公告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "公告ID", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "isRelease", required = true, value = "是否发布", dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "title", required = true, value = "公告标题", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "content", required = true, value = "公告内容", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/updatePlacard",method = RequestMethod.POST)
    public HttpResult updatePlacard(@RequestParam int id,
                                    @RequestParam boolean isRelease,
                                    @RequestParam String title,
                                    @RequestParam String content){
        int releaseStatus = isRelease ? 1 : 0;
        int result = 0;
        if(id > 0){
            result = merchantInfoService.updatePlacard(id,title, content,releaseStatus);
        }else{
            result = merchantInfoService.addPlacard(title, content,releaseStatus);
        }

        if(result > 0){
            return new HttpResult().fillCode(CodeEnum.SUCCESS);
        }

        return new HttpResult().fillCode(CodeEnum.ERROR_SERVER);
    }

    @ApiOperation("获取公告列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sortType", required = true, value = "根据Id排序方式,0 正序 1 倒序", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageIndex", required = true, value = "页码", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", required = true, value = "页面数据大小", dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "/findPlacards",method = RequestMethod.POST)
    public HttpResult<IPage> findPlacards(@RequestParam int sortType,
                                                 @RequestParam int pageIndex,
                                                 @RequestParam int pageSize){
        IPage placards = merchantInfoService.findPlacards(sortType, pageIndex, pageSize);
        return new HttpResult().fillData(placards);
    }

    @ApiOperation("获取最新发布公告")
    @RequestMapping(value = "/0/findNewReleasePlacard",method = RequestMethod.POST)
    public HttpResult<Placard> findNewReleasePlacard(){
        Placard placard = merchantInfoService.findNewReleasePlacard();
        if(placard != null) {
            return new HttpResult().fillData(placard);
        }else{
            return new HttpResult<>().fillCode(404,"没有发布的公告信息");
        }
    }

    @ApiOperation("获取最新公告信息")
    @RequestMapping(value = "/findNewPlacard",method = RequestMethod.POST)
    public HttpResult<Placard> findNewPlacard(){
        Placard placard = merchantInfoService.findPlacard();
        if(placard != null) {
            return new HttpResult().fillData(placard);
        }else{
            return new HttpResult<>().fillCode(404,"没有公告信息");
        }
    }


    @ApiOperation("添加商户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", required = true, value = "信息名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "val", required = true, value = "值", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/addMerchantInfo",method = RequestMethod.POST)
    public HttpResult addMerchantInfo(@RequestParam String name,
                                      @RequestParam String val){
        int result = merchantInfoService.addMerchantInfo(name, val);
        if(result > 0){
            return new HttpResult().fillCode(CodeEnum.SUCCESS);
        }

        return new HttpResult().fillCode(CodeEnum.ERROR_SERVER);
    }


    @ApiOperation("更新商户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", required = true, value = "信息名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "val", required = true, value = "值", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/updateMerchantInfo",method = RequestMethod.POST)
    public HttpResult updateMerchantInfo(@RequestParam int id,
                                         @RequestParam String name,
                                         @RequestParam String val){
        int result = 0;
        if(id > 0){
            result = merchantInfoService.updateMerchantInfo(id, name, val);
        }else{
            result = merchantInfoService.addMerchantInfo(name, val);
        }

        if(result > 0){
            return new HttpResult().fillCode(CodeEnum.SUCCESS);
        }
        return new HttpResult().fillCode(CodeEnum.ERROR_SERVER);
    }

    @ApiOperation("获取商户信息")
    @RequestMapping(value = "/0/findMerchantInfo",method = RequestMethod.POST)
    public HttpResult<MerchantInfo> findMerchantInfo(){
        MerchantInfo merchantInfo = merchantInfoService.findMerchantInfo();
        return new HttpResult().fillData(merchantInfo);
    }

}
