package com.drainage.intercprot;

import com.drainage.entity.Admin;
import com.drainage.utils.GlobalConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hrd <br/>
 * @date 2020/12/3
 */
public class LoginInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Admin admin = (Admin)request.getSession().getAttribute(GlobalConst.USER_SESSION_KEY);
//        if(admin == null){
//            response.sendRedirect("/login");
//            return false;
//        }

        return true;
    }
}
