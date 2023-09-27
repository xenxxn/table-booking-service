package com.xenxxn.tablebooking.learning;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class PasswordEncoderTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void passwordEncode() throws Exception{
        //given
        String password = "이제인leejane";
        //when
        String encodePassword = passwordEncoder.encode(password);
        String encodePassword2 = passwordEncoder.encode(password);
        //then
        assertThat(encodePassword).isNotEqualTo(encodePassword2);
    }

    @Test
    void passwordEncodeMatch(){
        //given
        String password = "이제인leejane";
        //when
        String encodePassword = passwordEncoder.encode(password);
        //then
        assertThat(passwordEncoder.matches(password, encodePassword)).isTrue();
    }
}
