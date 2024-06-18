package com.jun.blogducinemaback.adapter.`in`.filter

import com.jun.blogducinemaback.global.utils.JwtUtil
import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter


class JwtFilter(private val jwtUtil: JwtUtil): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        //request에서 Authorization 헤더를 찾음
        var jwtToken = ""

        if (request.cookies != null) {
            for (cookie in request.cookies) {
                if (cookie.name == "jwt-token") {
                    jwtToken = cookie.value
                    break
                }
            }
        }

        //Authorization 헤더 검증
        if (jwtToken.isEmpty()) {
            logger.info("token is null.")

            //조건이 해당되면 메소드 종료 (필수)
            filterChain.doFilter(request, response)
            return
        }

        logger.info("authorization starts now")

        try {
            //토큰 소멸 시간 검증
            if (jwtUtil.isExpired(jwtToken)) {
                logger.info("token is expired")

                //조건이 해당되면 메소드 종료 (필수)
                filterChain.doFilter(request, response)
                return
            }

            //토큰에서 username과 role 획득
            val username: String = jwtUtil.getUsername(jwtToken)
            val authorities =  jwtUtil.getAuthorities(jwtToken)

            //스프링 시큐리티 인증 토큰 생성
            val authToken: Authentication =
                UsernamePasswordAuthenticationToken(username, jwtToken, authorities)

            //세션에 사용자 등록
            SecurityContextHolder.getContext().authentication = authToken

        } catch (e: JwtException) {
            logger.error("JWT exception", e)
        } finally {
            filterChain.doFilter(request, response)
        }
    }
}