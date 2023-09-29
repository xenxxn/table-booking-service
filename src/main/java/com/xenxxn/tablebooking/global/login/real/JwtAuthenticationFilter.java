//package com.xenxxn.tablebooking.global.login.real;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    public static final String TOKEN_HEADER = "Authorization";
//    public static final String TOKEN_PREFIX = "Bearer ";
//
//    private final TokenProvider tokenProvider;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String token = this.resolveTokenFromRequest(request);
//
//        // token의 유효성
//        if (StringUtils.hasText(token) && this.tokenProvider.validateToken(token)) {
//            Authentication auth = this.tokenProvider.getAuthentication(token);
//            SecurityContextHolder.getContext().setAuthentication(auth);
//        }
//        if (token != null) {
//            log.info(String.format("[%s] -> [%s]", this.tokenProvider.getUserId(token), request.getRequestURI()));
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private String resolveTokenFromRequest(HttpServletRequest request) {
//
//        String token = request.getHeader(TOKEN_HEADER);
//
//        if (!ObjectUtils.isEmpty(token) && token.startsWith(TOKEN_PREFIX)) {
//            return token.substring(TOKEN_PREFIX.length());
//        }
//
//        return null;
//    }
//}