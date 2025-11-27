package com.codeWithhHemant.blog.security;

import com.codeWithhHemant.blog.paylods.JwtErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper mapper = new ObjectMapper();   //to convert java object to json

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        String path = (String) request.getAttribute("jakarta.servlet.error.request_uri");
        if(path == null) {
            path = request.getRequestURI(); // fallback
        }

        JwtErrorResponse errorResponse = new JwtErrorResponse(
                401,
                "Unauthorized",
                "Access Denied => Unauthorized User !!!",
                path
        );

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        mapper.writeValue(response.getWriter(), errorResponse); //converting java object to json(because content type is json) and writing json data using writer
    }
}