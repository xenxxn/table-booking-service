package com.xenxxn.tablebooking.controller;

import com.xenxxn.tablebooking.Role;
import com.xenxxn.tablebooking.entity.Member;
import com.xenxxn.tablebooking.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
public class TestController {
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager entityManager;
    @PostMapping("/register")
    public String register(Member member) {

        try{
            String updatePassword = "1234";

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            List<Member> list = memberRepository.findAll();

            //when
            Member findMember = memberRepository.findByUsername("testusername").orElseThrow(() -> new Exception());
            findMember.updatePassword(passwordEncoder,updatePassword);
            entityManager.flush();
            entityManager.clear();

            return "success";
        }catch(Exception ex){
            return ex.getMessage();
        }
    }


    @PostMapping("/login")
    public String login() {
        return "heyhey";
    }
}
