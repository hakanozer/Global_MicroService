package com.works.configs;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class GlobalFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("SERVER UP");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String url = req.getRequestURI();
        String[] urls = {"/customer/login", "/customer/register", "/error"};
        boolean status = true;
        for( String item : urls ) {
            if (item.equals(url)) {
                status = false;
                break;
            }
        }

        if ( status ) {
            boolean loginStatus = req.getSession().getAttribute("customer") == null;
            if (loginStatus) {
                res.sendRedirect("/error");
            }else {
                filterChain.doFilter(req, res);
            }
        }else {
            filterChain.doFilter(req, res);
        }



    }

    @Override
    public void destroy() {
        System.out.println("SERVER DOWN");
    }

}
