package com.xenxxn.tablebooking.global.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xenxxn.tablebooking.Role;
import com.xenxxn.tablebooking.entity.MemberEntity;
import com.xenxxn.tablebooking.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class LoginTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    ObjectMapper objectMapper = new ObjectMapper();

    private static String KEY_MEMBER_EMAIL = "memberEmail";
    private static String KEY_PASSWORD = "password";
    private static String MEMBEREMAIL= "memberEmail";
    private static String PASSWORD = "123456789";
    private static String LOGIN_URL = "/login";

    private void clear() {
        entityManager.flush();
        entityManager.clear();
    }

    @BeforeEach
    private void init() {
        memberRepository.save(MemberEntity.builder()
                        .memberEmail(MEMBEREMAIL)
                        .password(delegatingPasswordEncoder.encode(PASSWORD))
                        .memberType(Role.USER)
                .build());
        clear();

    }
    private Map getMemberEmailPasswordMap (String memberEmail, String password) {
        Map<String, String> map = new HashMap<>();
        map.put(KEY_MEMBER_EMAIL, memberEmail);
        map.put(KEY_PASSWORD, password);
        return map;
    }

    private ResultActions perform(String url, MediaType mediaType, Map usernamePasswordMap) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders
                        .post(url)
                        .contentType(mediaType)
                        .content(objectMapper.writeValueAsString(usernamePasswordMap)));
    }

    @Test
    void loginSuccess() throws Exception{
        //given
        Map<String, String> map = getMemberEmailPasswordMap(MEMBEREMAIL, PASSWORD);
        //when
        //then
        MvcResult result = perform(LOGIN_URL, MediaType.APPLICATION_JSON, map)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void loginFail_EmailNotMatch() throws Exception{
        //given
        Map<String, String> map = getMemberEmailPasswordMap(MEMBEREMAIL+"123", PASSWORD);
        //when
        //then

        MvcResult result = perform(LOGIN_URL, MediaType.APPLICATION_JSON, map)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void loginFail_PasswordNotMatch() throws Exception{
        //given
        Map<String, String> map = getMemberEmailPasswordMap(MEMBEREMAIL, PASSWORD+"123");
        //when
        //then

        MvcResult result = perform(LOGIN_URL, MediaType.APPLICATION_JSON, map)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void loginUrlNotMatch_FORBIDDEN() throws Exception {
        //given
        Map<String, String> map = getMemberEmailPasswordMap(MEMBEREMAIL, PASSWORD);
        //when
        //then
        perform(LOGIN_URL+"123", MediaType.APPLICATION_JSON, map)
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void loginDataIsNotJson() throws Exception{
        //given
        Map<String, String> map = getMemberEmailPasswordMap(MEMBEREMAIL, PASSWORD);
        //when
        perform(LOGIN_URL, APPLICATION_FORM_URLENCODED, map)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        //then
    }

    @Test
    void loginHttpMethodGet() throws Exception{
        //given
        Map<String, String> map = getMemberEmailPasswordMap(MEMBEREMAIL, PASSWORD);
        //when
        mockMvc.perform(MockMvcRequestBuilders
                .get(LOGIN_URL)
                .contentType(APPLICATION_FORM_URLENCODED)
                .content(objectMapper.writeValueAsString(map)))
                .andDo(print())
                .andExpect(status().isNotFound());
        //then
    }

    @Test
    void loginHttpMethodPut() throws Exception{
        //given
        Map<String, String> map = getMemberEmailPasswordMap(MEMBEREMAIL, PASSWORD);
        //when
        mockMvc.perform(MockMvcRequestBuilders
                        .put(LOGIN_URL)
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .content(objectMapper.writeValueAsString(map)))
                .andDo(print())
                .andExpect(status().isNotFound());
        //then
    }

}
