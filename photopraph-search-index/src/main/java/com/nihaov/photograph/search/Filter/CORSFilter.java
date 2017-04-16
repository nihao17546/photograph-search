package com.nihaov.photograph.search.Filter;

import com.google.common.base.Strings;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by nihao on 16/12/7.
 */
public class CORSFilter implements Filter {
    private FilterConfig filterConfig;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest res = (HttpServletRequest) request;
        HttpServletResponse req = (HttpServletResponse) response;
        req.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:8080");
        req.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        req.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        chain.doFilter(res, req);
//        Cookie[] cookies = res.getCookies();
//        int status = 401;
//        if(cookies!=null){
//            for(Cookie cookie:cookies){
//                String name = cookie.getName();
//                if("1_&photopraph".equals(name)){
//                    String value = cookie.getValue();
//                    if(!Strings.isNullOrEmpty(value)){
//                        try{
//                            Long val = Long.parseLong(value.substring(0,value.indexOf("&")));
//                            if(Math.abs(System.currentTimeMillis()-val)<60000){
//                                chain.doFilter(res, req);
//                            }
//                            else{
//                                status = 412;
//                            }
//                        }catch (StringIndexOutOfBoundsException e){
//                            status = 403;
//                        }catch (NumberFormatException e){
//                            status = 403;
//                        }
//                    }
//                }
//            }
//        }
//        req.setStatus(status);
    }

    @Override
    public void destroy() {

    }
}
