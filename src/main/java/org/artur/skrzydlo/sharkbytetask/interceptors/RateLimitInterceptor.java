package org.artur.skrzydlo.sharkbytetask.interceptors;

import org.artur.skrzydlo.sharkbytetask.services.RateLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class RateLimitInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RateLimitService rateLimitService;

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler)
            throws Exception {

        rateLimitService.incrementLimit();

        if (request.getMethod().equals(HttpMethod.GET.name()) && !rateLimitService.validateLimit()) {

            response.sendError(429, "Rate limit exceeded");
            return false;
        }

        return true;
    }

}
