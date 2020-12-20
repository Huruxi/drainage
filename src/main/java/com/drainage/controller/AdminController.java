package com.drainage.controller;

import com.drainage.dto.CodeEnum;
import com.drainage.dto.HttpResult;
import com.drainage.entity.Admin;
import com.drainage.service.IAdminService;
import com.drainage.utils.GlobalConst;
import com.drainage.utils.VerifyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

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


    @ApiOperation("获取验证码")
    @GetMapping(value = "/0/getcode")
    public void getCode(HttpServletResponse response, HttpServletRequest request) throws Exception {
        // 获取到session
        HttpSession session = request.getSession();
        // 取到sessionid
        String id = session.getId();

        // 利用图片工具生成图片
        // 返回的数组第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = VerifyUtil.newBuilder()
                .setWidth(120)   //设置图片的宽度
                .setHeight(35)   //设置图片的高度
                .setSize(6)      //设置字符的个数
                .setLines(10)    //设置干扰线的条数
                .setFontSize(25) //设置字体的大小
                .setTilt(true)   //设置是否需要倾斜
                .setBackgroundColor(Color.LIGHT_GRAY) //设置验证码的背景颜色
                .build()         //构建VerifyUtil项目
                .createImage();  //生成图片
        // 将验证码存入Session
        session.setAttribute("SESSION_VERIFY_CODE_" + id, objs[0]);
        // 将图片输出给浏览器
        BufferedImage image = (BufferedImage) objs[1];
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
    }
}
