package br.com.arcnal.arcnal.utils;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ClientIpFilter implements Filter {

    public static final String CLIENT_IP_ATTR = "CLIENT_IP";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String clientIp = extractClientIp(httpServletRequest);

        httpServletRequest.setAttribute(CLIENT_IP_ATTR, clientIp);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String extractClientIp(HttpServletRequest request){
        String forwarded = request.getHeader("X-Forwarded-For");
        if(forwarded != null && !forwarded.isBlank()){
            return forwarded.split(",")[0];
        }
        return request.getRemoteAddr();
    }
}
