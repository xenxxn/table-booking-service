package com.xenxxn.tablebooking.global.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xenxxn.tablebooking.Role;
import com.xenxxn.tablebooking.entity.MemberEntity;
import com.xenxxn.tablebooking.global.jwt.Service.JwtService;
import com.xenxxn.tablebooking.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class JwtServiceTest {
    @Autowired
    JwtService jwtService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager entityManager;

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.access.header}")
    private String accessHeader;
    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String USERNAME_CLAIM = "memberEmail";
    private static final String BEARER = "Bearer ";

    private String memberEmail = "username";

    @BeforeEach
    public void init() {
        MemberEntity member = MemberEntity.builder()
                .memberEmail(memberEmail)
                .password("1234567890")
                .phone("01012345678")
                .memberType(Role.USER)
                .build();
        memberRepository.save(member);
        clear();
    }

    private void clear(){
        entityManager.flush();
        entityManager.clear();
    }

    private DecodedJWT getVerify(String token) {
        return JWT.require(Algorithm.HMAC512(secret)).build().verify(token);
    }

    @Test
    void createAccessToken() throws Exception{
        //given
        String accessToken = jwtService.createAccessToken(memberEmail);
        DecodedJWT verify = getVerify(accessToken);
        String subject = verify.getSubject();
        String findMemberEmail = verify.getClaim(USERNAME_CLAIM).asString();
        //when
        //then
        assertThat(findMemberEmail).isEqualTo(memberEmail);
        assertThat(subject).isEqualTo(ACCESS_TOKEN_SUBJECT);
    }

    @Test
    //리프레쉬 토큰은 memberEmail 없어야 함.
    void createRefreshToken() throws Exception{
        //given
        String refreshToken = jwtService.createRefreshToken();
        DecodedJWT verify = getVerify(refreshToken);
        String subject = verify.getSubject();
        String memberEmail = verify.getClaim(USERNAME_CLAIM).asString();
        //when
        //then
        assertThat(subject).isEqualTo(REFRESH_TOKEN_SUBJECT);
        assertThat(memberEmail).isNull();
    }

    @Test
    void updateRefreshToken() throws Exception{
        //given
        String refreshToken = jwtService.createRefreshToken();
        jwtService.updateRefreshToken(memberEmail, refreshToken);
        clear();
        Thread.sleep(3000);
        //when
        String reIssuedRefreshToken = jwtService.createRefreshToken();
        jwtService.updateRefreshToken(memberEmail, reIssuedRefreshToken);
        //then
        assertThrows(Exception.class, () -> memberRepository.findByRefreshToken(refreshToken).get());
        assertThat(memberRepository.findByRefreshToken(reIssuedRefreshToken).get().getMemberEmail()).isEqualTo(memberEmail);
    }

    @Test
    void destroyRefreshToken() throws Exception{
        //given
        String refreshToken = jwtService.createRefreshToken();
        jwtService.updateRefreshToken(memberEmail, refreshToken);
        clear();
        //when
        jwtService.destroyRefreshToken(memberEmail);
        clear();
        //then
        assertThrows(Exception.class, () -> memberRepository.findByRefreshToken(refreshToken).get());
        MemberEntity member = memberRepository.findByMemberEmail(memberEmail).get();
        assertThat(member.getRefreshToken()).isNull();
    }

    @Test
    void setAccessTokenHeader() throws Exception{
        //given
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

        String accessToken = jwtService.createAccessToken(memberEmail);
        String refreshToken = jwtService.createRefreshToken();

        jwtService.setAccessTokenHeader(mockHttpServletResponse, accessToken);
        //when
        jwtService.sendToken(mockHttpServletResponse, accessToken, refreshToken);
        //then
        String headerAccessToken = mockHttpServletResponse.getHeader(accessHeader);
        assertThat(headerAccessToken).isEqualTo(accessToken);
    }

    @Test
    void setRefreshTokenHeader() throws Exception {
        //given
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

        String accessToken = jwtService.createAccessToken(memberEmail);
        String refreshToken = jwtService.createRefreshToken();

        jwtService.setRefreshTokenHeader(mockHttpServletResponse, refreshToken);
        //when
        jwtService.sendToken(mockHttpServletResponse, accessToken, refreshToken);
        //then
        String headerRefreshToken = mockHttpServletResponse.getHeader(refreshHeader);

        assertThat(headerRefreshToken).isEqualTo(refreshToken);
    }

    @Test
    void sendToken() throws Exception{
        //given
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

        String accessToken = jwtService.createAccessToken(memberEmail);
        String refreshToken = jwtService.createRefreshToken();
        //when
        jwtService.sendToken(mockHttpServletResponse, accessToken, refreshToken);
        //then
        String headerAccessToken = mockHttpServletResponse.getHeader(accessHeader);
        String headerRefreshToken = mockHttpServletResponse.getHeader(refreshHeader);
        assertThat(headerAccessToken).isEqualTo(accessToken);
        assertThat(headerRefreshToken).isEqualTo(refreshToken);
    }

    private HttpServletRequest setRequest(String accessToken, String refreshToken) throws IOException {
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        jwtService.sendToken(mockHttpServletResponse, accessToken, refreshToken);

        String headerAccessToken = mockHttpServletResponse.getHeader(accessHeader);
        String headerRefreshToken = mockHttpServletResponse.getHeader(refreshHeader);

        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();

        httpServletRequest.addHeader(accessHeader, BEARER + headerAccessToken);
        httpServletRequest.addHeader(refreshHeader, BEARER + headerRefreshToken);

        return httpServletRequest;
    }

    @Test
    void extractAccessToken() throws Exception{
        //given
        String accessToken = jwtService.createAccessToken(memberEmail);
        String refreshToken = jwtService.createRefreshToken();
        HttpServletRequest httpServletRequest = setRequest(accessToken, refreshToken);
        //when
        String extractAccessToken = jwtService.extractAccessToken(httpServletRequest);
        //then
        assertThat(extractAccessToken).isEqualTo(accessToken);
        assertThat(getVerify(extractAccessToken).getClaim(USERNAME_CLAIM)
                .asString()).isEqualTo(memberEmail);
    }

    @Test
    void extractRefreshToken() throws Exception{
        //given
        String accessToken = jwtService.createAccessToken(memberEmail);
        String refreshToken = jwtService.createRefreshToken();
        HttpServletRequest httpServletRequest = setRequest(accessToken, refreshToken);
        //when
        String extractRefreshToken = jwtService.extractRefreshToken(httpServletRequest);
        //then
        assertThat(extractRefreshToken).isEqualTo(refreshToken);
        assertThat(getVerify(extractRefreshToken).getSubject()).isEqualTo(REFRESH_TOKEN_SUBJECT);
    }

    @Test
    void extractMemberEmail() throws Exception{
        //given
        String accessToken = jwtService.createAccessToken(memberEmail);
        String refreshToken = jwtService.createRefreshToken();
        HttpServletRequest httpServletRequest = setRequest(accessToken, refreshToken);

        String requestAccessToken = jwtService.extractAccessToken(httpServletRequest);
        //when
        String extractMemberEmail = jwtService.extractMemberEmail(requestAccessToken);
        //then
        assertThat(extractMemberEmail).isEqualTo(memberEmail);
    }
}
