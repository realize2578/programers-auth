package com.back.global.rq;

import com.back.domain.member.entity.Member;
import com.back.domain.member.service.MemberService;
import com.back.global.exception.ServiceException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Arrays;

@Component
@RequestScope
@RequiredArgsConstructor
public class Rq {

    private final HttpServletRequest request;
    private final MemberService memberService;
    private final HttpServletResponse response;

    public void addCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true); // 쿠키를 자바스크립트로 조작할 수 있냐 없냐. true 조작 안되게
        cookie.setDomain("localhost"); // localhost 도메인에서만 사용 가능하도록 지정

        response.addCookie(
                cookie
        );
    }

    public Member getActor() {

        String authorizationHeader = request.getHeader("Authorization");

        String apiKey;
        //헤더 방식 vs 쿠키 방식
        if (authorizationHeader != null ){
            if(!authorizationHeader.startsWith("Bearer ")) {
                throw new ServiceException("401-2", "잘못된 형식입니다.");
            }
            apiKey =authorizationHeader.replace("Bearer ","");
        } else{
            apiKey = request.getCookies() == null? ""
                    : Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals("apiKey"))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse("");
        }

        if(apiKey.isBlank()){
            throw new ServiceException("401-3","인증 데이터가 존재하지 않습니다.");
        }

        return memberService.findByApiKey(apiKey).orElseThrow(
                ()-> new ServiceException("401-1", "유효하지 않은 API 키입니다.")
        );
    }

    public void deleteCookie(String name) {
        Cookie cookie = new Cookie(name, ""); //덮어 씌움
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setDomain("localhost");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }
}
