package org.hrd.homeworkspringdatajpa.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profile")
public class UserController {

    @GetMapping("/my-profile")
    public String getMyProfile(Authentication authentication) {
        if(authentication.getPrincipal() instanceof OAuth2User oauthUser) {
            String name = oauthUser.getAttribute("name");
            return "Welcome, " + name;
        } else {
            String username = authentication.getName();
            return "Welcome, " + username;
        }
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //Clear authentication
        if(auth != null){
            SecurityContextHolder.clearContext();
            request.getSession().invalidate();
        }
//        response.sendRedirect("LogoutSuccess.html");
        return "Logout Success.";
    }


}
