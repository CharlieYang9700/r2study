package com.code.interceptor;

import com.code.annotation.Login;
import com.code.biz.BizException;
import com.code.common.Constants;
import com.code.utils.CommonUtil;
import com.code.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author ping
 */
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod method = (HandlerMethod) handler;
//            verifyLogin(request,method);
        }
        return true;
    }

    /**
     * 登录验证
     * @param request 请求
     * @param method 方法
     * @throws BizException BizException
     */
    private void verifyLogin(HttpServletRequest request, HandlerMethod method) throws BizException {
        boolean isLogin;
        Login loginAn =method.getMethod().getDeclaredAnnotation(Login.class);
        if(loginAn != null){
            isLogin = true;
        }
        String token = request.getHeader(Constants.TOKEN);
        log.info("token={}",token);
        if(CommonUtil.isEmpty(token)){
            token = request.getParameter(Constants.TOKEN);
            throw new BizException("您已下线");
        }
    }
}
