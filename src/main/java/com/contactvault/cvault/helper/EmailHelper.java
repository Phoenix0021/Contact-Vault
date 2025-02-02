package com.contactvault.cvault.helper;
 import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class EmailHelper {

    

    public static String getEmailOfLoggedInUser(Authentication authentication) {

        if (authentication instanceof OAuth2AuthenticationToken) {

            var aOAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            var clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oauth2User = (OAuth2User) authentication.getPrincipal();
            String username = "";

            if (clientId.equalsIgnoreCase("google")) {

                System.out.println("Getting email from google");
                username = oauth2User.getAttribute("email").toString();

            } else if (clientId.equalsIgnoreCase("github")) {

                System.out.println("Getting email from github");
                username = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString()
                        : oauth2User.getAttribute("login").toString() + "@gmail.com";
            }

            return username;

        } else {
            System.out.println("Getting data from local database");
            return authentication.getName();
        }

    }

    public static String getLinkForEmailVerificatiton(String emailVerificationToken) {

        return "http://localhost:8080/auth/verify-email?token=" + emailVerificationToken;

    }

}
