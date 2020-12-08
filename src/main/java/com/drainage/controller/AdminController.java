package com.drainage.controller;

import com.drainage.dto.CodeEnum;
import com.drainage.dto.HttpResult;
import com.drainage.entity.Admin;
import com.drainage.service.IAdminService;
import com.drainage.utils.GlobalConst;
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
 * @date 2020/12/1
 */
@Api(tags = "管理员模块")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private IAdminService adminService;

    @ApiOperation("管理员登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", required = true, value = "管理员名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "passwrod", required = true, value = "登录密码", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/0/loginUser", method = RequestMethod.POST)
    public HttpResult loginUser(@RequestParam String name,
                                @RequestParam String passwrod) {

        Admin admin = adminService.findAdmin(name);
        if (admin == null) {
            return new HttpResult().fillCode(CodeEnum.ERROR_PARAMETER);
        }

        if (passwrod.equals(admin.getPassword())) {
            request.getSession().setAttribute(GlobalConst.USER_SESSION_KEY, admin);
            System.out.println(request.getSession().getId());
            return new HttpResult().fillCode(CodeEnum.SUCCESS);
        }

        return new HttpResult().fillCode(CodeEnum.ERROR_PARAMETER);
    }


    @ApiOperation("退出登录")
    @RequestMapping(value = "/outLogin", method = RequestMethod.POST)
    public HttpResult outLogin() {
        request.getSession().removeAttribute(GlobalConst.USER_SESSION_KEY);
        return new HttpResult().fillCode(CodeEnum.SUCCESS);
    }


    @ApiOperation("添加管理员")
    @RequestMapping(value = "/addAdmin", method = RequestMethod.POST)
    public HttpResult addAdmin(@RequestParam String name,
                               @RequestParam String passwrod) {

        Admin admin = new Admin();
        admin.setName(name);
        admin.setPassword(passwrod);
        int result = adminService.addAdmin(admin);
        if (result == 1) {
            return new HttpResult().fillCode(CodeEnum.SUCCESS);
        }

        return new HttpResult().fillCode(CodeEnum.ERROR_PARAMETER);
    }

}
