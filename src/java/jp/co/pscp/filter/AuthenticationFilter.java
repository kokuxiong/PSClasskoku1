/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.pscp.filter;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jp.co.pscp.controller.LoginController;
import jp.co.pscp.util.ConstUtil;

/**
 *
 * @author 雄
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class AuthenticationFilter implements Filter{
    
    //依赖注入
    //一次请求只能有一个LoginController的对象
    @Inject
    private LoginController login;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        
        //只有HttpServletRequest的对象才有getRequestURI()方法，所以上面强行转换了下
        String reqURI = req.getRequestURI();
        boolean hasLogined = false;
        //初次登陆，index.xhtml
        reqURI = reqURI.endsWith("/") ? reqURI.concat(ConstUtil.URI_INDEX) : reqURI;
        //登陆后判断
        hasLogined = login != null && login.isHasLogined();
        //特定的画面不需要login
        if(reqURI.contains(ConstUtil.URI_LOGIN) || reqURI.contains(ConstUtil.URI_INDEX)
                || reqURI.contains(ConstUtil.RESOURCE)
                || hasLogined){
            chain.doFilter(request, response);
        }else{
            res.sendRedirect(req.getContextPath().concat("/").concat(ConstUtil.URI_LOGIN));
        }
        
        //判断，如果login了则继续
        
    }

    @Override
    public void destroy() {
    }
    
}
