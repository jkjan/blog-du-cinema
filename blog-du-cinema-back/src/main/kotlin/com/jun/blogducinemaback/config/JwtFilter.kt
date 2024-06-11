package com.jun.blogducinemaback.config

import com.jun.blogducinemaback.entity.Authority
import com.jun.blogducinemaback.entity.UserData
import com.jun.blogducinemaback.services.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.NegatedRequestMatcher
import org.springframework.web.filter.OncePerRequestFilter


class JwtFilter(private val jwtUtil: JwtUtil): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        //request에서 Authorization 헤더를 찾음
        val authorization = request.getHeader("authorization")
        logger.info("Authorization header: $authorization")

        //Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            logger.info("token is null.")

            //조건이 해당되면 메소드 종료 (필수)
            filterChain.doFilter(request, response)
            return
        }

        logger.info("authorization starts now")

        //Bearer 부분 제거 후 순수 토큰만 획득
        val token = authorization.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]

        //토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {
            logger.info("token expired")

            //조건이 해당되면 메소드 종료 (필수)
            filterChain.doFilter(request, response)
            return
        }

        //토큰에서 username과 role 획득
        val username: String = jwtUtil.getUsername(token)
        val authoritiesString: List<String> = jwtUtil.getAuthorities(token)

        val authorities = authoritiesString.map { authority ->
            Authority(authority)
        }

        //스프링 시큐리티 인증 토큰 생성
        val authToken: Authentication =
            UsernamePasswordAuthenticationToken(username, token, authorities)

        //세션에 사용자 등록
        SecurityContextHolder.getContext().authentication = authToken

        logger.info("$authoritiesString user")

        filterChain.doFilter(request, response)
    }
}