package edu.northeastern.reggie.filter;


import com.alibaba.fastjson.JSON;
import edu.northeastern.reggie.common.BaseContext;
import edu.northeastern.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * check the user have login or not.
 * If didnt login, will return back to login page.
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        /*
        1. get the request URL
         */
        String requestURI = request.getRequestURI();

//        log.info("Intercept the request: {} ... !!! ", requestURI);

        // urls no need for checking in filter
        String[] urls = new String[] {
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };

        /*
        2. decide this request URL need to address with the filter or not.
            Not all request URL needs to check login status. and If already login, wont need to check
         */
        boolean check = check(urls, requestURI);

        /*
        3. no need for checking, green light
         */
        if (check) {
//            log.info("This Response No Need checking, {} ... !!! ", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        /*
        4-1. do need to check. So, check user login status
         */
        if (request.getSession().getAttribute("employee") != null ) {
//            log.info("Already login with {} ... !!! ", request.getSession().getAttribute("employee"));

            Long empId = (Long)request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);

            filterChain.doFilter(request, response);
            return;
        }

        /*
        4-2. do need to check. So, check user login status, for front end
         */
        if (request.getSession().getAttribute("user") != null ) {
//            log.info("Already login with {} ... !!! ", request.getSession().getAttribute("user"));

            Long empId = (Long)request.getSession().getAttribute("user");
            BaseContext.setCurrentId(empId);

            filterChain.doFilter(request, response);
            return;
        }

        /*
        5. if not login, using IO streaming, to response not Login at front page
         */
//        log.info("Not Login yet ... !!!  ");
        response.getWriter().write(JSON.toJSONString(R.error("NOT_LOGIN")));
    }

    /**
     * check the urls, might need to check status.
     * @param urls
     * @param requestURL
     * @return
     */
    public boolean check(String[] urls, String requestURL) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURL);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
