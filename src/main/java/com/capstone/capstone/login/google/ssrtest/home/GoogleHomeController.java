package com.capstone.capstone.login.google.ssrtest.home;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//@Controller
//public class GoogleHomeController {
//    @GetMapping("/login/google")
////    인증된 Authenction 을 파라미터로 전달 받는 것도 가능
////    public String home(Authentication authentication) {
////        var oAuth2User = (OAuth2User)authentication.getPrincipal();
////    OAuth2User 객체를 파라미터로 직접 전달 받는 것도 가능
////    public String home(@AuthenticationPrincipal OAuth2User oAuth2User) {
//    public String google() {
//        var oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println(oAuth2User);
//        System.out.println("User's email in Google: " + oAuth2User.getAttributes().get("email"));
//
//        return "google-oauth2";
//    }
//}
