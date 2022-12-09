package com.capstone.capstone.login.auth.handler;

import com.capstone.capstone.login.jwt.JwtTokenizer;
import com.capstone.capstone.member.entity.Member;
import com.capstone.capstone.member.service.MemberService;
import com.capstone.capstone.login.utils.CustomAuthorityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// OAuth2인증 후, JWT 생성 및 프론트로 JWT 전달
@Service
public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final MemberService memberService;

    public OAuth2MemberSuccessHandler(JwtTokenizer jwtTokenizer,
                                      CustomAuthorityUtils authorityUtils,
                                      MemberService memberService) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
        this.memberService = memberService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String registrationId = request.getRequestURI().substring(request.getRequestURI().lastIndexOf('/') + 1);

//        google
        if(registrationId.equals("google")) {
            var oAuth2User = (OAuth2User)authentication.getPrincipal();
//            System.out.println(oAuth2User.toString());
            String email = String.valueOf(oAuth2User.getAttributes().get("email"));
            List<String> authorities = authorityUtils.createRoles(email);           // CustomAuthorityUtils 를 이용해 권한 정보 생성

            saveMember(email);
            redirect(request, response, email, authorities);  // 프론트에 리다이렉트
        }

//        kakao
        else if(registrationId.equals("kakao")) {
            var oAuth2User = (OAuth2User)authentication.getPrincipal();
            Map<String, Object> attributes = oAuth2User.getAttributes();
            Map<String, Object> kakao_account = (Map<String, Object>)attributes.get("kakao_account");
            String email = String.valueOf(kakao_account.get("email"));
            List<String> authorities = authorityUtils.createRoles(email);           // CustomAuthorityUtils 를 이용해 권한 정보 생성

            saveMember(email);
            redirect(request, response, email, authorities);  // 프론트에 리다이렉트
        }
    }

    private void saveMember(String email) {
        Member member = new Member(email);
        memberService.createMember(member);
    }

    // 리다이렉트
    private void redirect(HttpServletRequest request, HttpServletResponse response, String username, List<String> authorities) throws IOException {
        // 토큰 생성
        String accessToken = delegateAccessToken(username, authorities);
        String refreshToken = delegateRefreshToken(username);

//        String uri = createURI(accessToken, refreshToken).toString();
        String uri = createURI(username, accessToken, refreshToken).toString();
        getRedirectStrategy().sendRedirect(request, response, uri);   //  SimpleUrlAuthenticationSuccessHandler 에서 제공하는 sendRedirect() 메서드를 이용해 프론트 쪽으로 리다이렉트
    }

    // 엑세스 토큰 생성 대행
    private String delegateAccessToken(String username, List<String> authorities) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);       // 일단 username = email
        claims.put("roles", authorities);

        String subject = username;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    // 리프레시 토큰 생성 대행
    private String delegateRefreshToken(String username) {
        String subject = username;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }

    // 토큰만 있는 URI 생성
    private URI createURI(String accessToken, String refreshToken) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_token", accessToken);
        queryParams.add("refresh_token", refreshToken);

        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("fashionboomer.tk")
//                .port(80)
                .path("/receive-token.html")
                .queryParams(queryParams)
                .build()
                .toUri();
    }

    // username 있는 URI 생성
    private URI createURI(String username, String accessToken, String refreshToken) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("user_name", username);
        queryParams.add("access_token", accessToken);
        queryParams.add("refresh_token", refreshToken);

        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("fashionboomer.tk")
//                .port(80)
                .path("/receive-token.html")
                .queryParams(queryParams)
                .build()
                .toUri();
    }
}
