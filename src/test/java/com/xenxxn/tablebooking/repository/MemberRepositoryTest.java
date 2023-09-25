package com.xenxxn.tablebooking.repository;

import com.xenxxn.tablebooking.Role;
import com.xenxxn.tablebooking.entity.MemberEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    public void memberSaveSuccess() {
        // Given
        MemberEntity memberEntity = MemberEntity.builder()
                .memberEmail("222@naver.com")
                .password("1234")
                .phone("01012341234")
                .memberType("USER")
                .registerDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        MemberEntity saveMember = memberRepository.save(memberEntity);
        MemberEntity findMember = memberRepository.findById(saveMember.getMemberId())
                .orElseThrow(() -> new RuntimeException("저장된 회원이 없습니다 "));

        // Then
        assertThat(findMember).isEqualTo(saveMember);
        assertThat(findMember).isEqualTo(memberEntity);
    }


    @Test
    void existsByMemberEmail() {
    }

    @Test
    void findByMemberEmail() {
    }
}