package com.book.bookapi.exceptions;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException {
        res.setContentType("application/json;charset=UTF-8");
        Map<String, String> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        if(req.getRequestURL().toString().endsWith("auth/sign-in")){
            res.setStatus(403);
            response.put("message", "Incorrect credentials");
        }
        else{
            res.setStatus(401);
            response.put("message", authException.getMessage());
        }
        res.getWriter().write((new Gson().toJson(response)));
    }
}