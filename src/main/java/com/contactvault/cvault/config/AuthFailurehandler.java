package com.contactvault.cvault.config;

import java.io.IOException;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.contactvault.cvault.helper.Message;
import com.contactvault.cvault.helper.MessageType;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthFailurehandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
            if(exception instanceof DisabledException){
                HttpSession session = request.getSession();
                session.setAttribute("message",  Message.builder().content("User is disabled").type(MessageType.red).build());
                response.sendRedirect("/login");

            }


    }
    
    
}
