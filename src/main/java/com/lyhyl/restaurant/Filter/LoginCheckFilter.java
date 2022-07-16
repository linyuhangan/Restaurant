package com.lyhyl.restaurant.Filter;

import com.alibaba.fastjson.JSON;
import com.lyhyl.restaurant.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*检查用户将是否完成登录
 */
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter  implements Filter {

    //路径匹配器
    public  static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        log.info("拦截到的请求：{}",request.getRequestURI());
        //获取当前访问路径
        String requestURI = request.getRequestURI();

        //设置无需过滤的资源
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "front/**"
        };

        //判断本次请求是否需要处理
        boolean check = check(urls, requestURI);

        //不需要处理直接放行
        if (check){
            log.info("路径{}",requestURI);
            filterChain.doFilter(request,response);
            return ;
        }
        //判断登陆状态，如果已经登陆则放行
        if (request.getSession().getAttribute("employee") != null){
            log.info("路径{}",requestURI);
            filterChain.doFilter(request,response);
            return ;
        }
        //如果未登录，返回未登录结果,通过输出流向页面返回消息
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        log.info("拦截路径{}",requestURI);
        return ;

    }

    /**
     * 路径匹配，检查本次匹配是否需要放行
     * @param urls
     * @param requestURL
     * @return
     */
    public  boolean check(String[] urls,String requestURL){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURL);
            if (match){
                return true;
            }

        }
        return false;
    }
}
