package com.jump.ecommerce.configuration;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, PATCH, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers",
                "X-Requested-With,Content-Type,Accept,Origin,Authorization,Content-Disposition");
        response.setHeader("Access-Control-Max-Age", "3600");
        if (request.getMethod() != "OPTIONS") {
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig filterConfig) {

    }

    public void destroy() {

    }

}