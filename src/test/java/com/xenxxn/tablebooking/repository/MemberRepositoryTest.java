package com.xenxxn.tablebooking.repository;

import com.xenxxn.tablebooking.Role;
import com.xenxxn.tablebooking.entity.MemberEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    private void clear(){
        entityManager.flush();
        entityManager.clear();
    }

    @AfterEach
    private void after(){
        entityManager.clear();
    }

    @Test
    public void memberSaveSuccess() {
        // Given
        MemberEntity memberEntity = MemberEntity.builder()
                .memberEmail("test@test.com")
                .password("1234")
                .phone("01012341234")
                .memberType(Role.USER)
                .build();
        MemberEntity saveMember = memberRepository.save(memberEntity);
        MemberEntity findMember = memberRepository.findById(saveMember.getMemberId())
                .orElseThrow(() -> new RuntimeException("저장된 회원이 없습니다 "));

        // Then
        assertThat(findMember).isEqualTo(saveMember);
        assertThat(findMember).isEqualTo(memberEntity);
    }

    @Test
    void notExistsId(){
        //given
        MemberEntity member = MemberEntity.builder()
                .password("1234567890")
                .phone("01012341234")
                .memberType(Role.USER)
                .build();
        //when
        assertThrows(Exception.class, () -> memberRepository.save(member));
        //then
    }

    @Test
    void successMemberInfoModify() throws Exception {
        //given
        MemberEntity member1 = MemberEntity.builder()
                .memberEmail("test@test.com")
                .password("1234")
                .phone("01012341234")
                .memberType(Role.USER)
                .build();
        memberRepository.save(member1);
        clear();

        String updatePassword = "update";
        String updatePhone = "01098765432";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //when
        MemberEntity findMember = memberRepository.findById(member1.getMemberId())
                .orElseThrow(() -> new Exception());
        findMember.updatePassword(passwordEncoder, updatePassword);
        findMember.updatePhone(updatePhone);
        entityManager.flush();


        //then
        MemberEntity findUpdateMember =
                memberRepository.findById(findMember.getMemberId())
                        .orElseThrow(() -> new Exception());

        assertThat(findUpdateMember).isSameAs(findMember);
        assertThat(passwordEncoder.matches(updatePassword, findUpdateMember.getPassword())).isTrue();
        assertThat(findUpdateMember.getPhone()).isEqualTo(updatePhone);
    }

    @Test
    void deleteMember() throws Exception{
        //given
        MemberEntity member = MemberEntity.builder()
                .memberEmail("test@test.com")
                .password("1234")
                .phone("01012341234")
                .memberType(Role.USER)
                .build();

        memberRepository.save(member);
        clear();
        //when
        memberRepository.delete(member);
        clear();
        //then
        assertThrows(Exception.class, () ->
                memberRepository.findById(member.getMemberId())
                        .orElseThrow(() -> new Exception()));
    }


    @Test
    void existsByMemberEmail() throws Exception{
        //given
        String memberEmail = "test@test.com";
        MemberEntity member = MemberEntity.builder()
                .memberEmail(memberEmail)
                .password("1234")
                .phone("01012341234")
                .memberType(Role.USER)
                .build();
        memberRepository.save(member);
        clear();
        //when, then
        assertThat(memberRepository.existsByMemberEmail(memberEmail)).isTrue();
        assertThat(memberRepository.existsByMemberEmail(memberEmail + "asdasd")).isFalse();
    }

    @Test
    void findByMemberEmail() throws Exception{
        //given
        String memberEmail = "test@test.com";
        MemberEntity member = MemberEntity.builder()
                .memberEmail(memberEmail)
                .password("1234")
                .phone("01012341234")
                .memberType(Role.USER)
                .build();
        memberRepository.save(member);
        clear();
        //when, then
        assertThat(memberRepository.findByMemberEmail(memberEmail).get().getMemberEmail()).isEqualTo(member.getMemberEmail());
        assertThat(memberRepository.findByMemberEmail(memberEmail).get().getMemberId()).isEqualTo(member.getMemberId());

        assertThrows(Exception.class, () -> memberRepository.findByMemberEmail(memberEmail+"sss").orElseThrow(() -> new Exception()));
    }

    @Test
    void registerTime() throws Exception{
        //given
        MemberEntity member = MemberEntity.builder()
                .memberEmail("test@test.com")
                .password("1234")
                .phone("01012341234")
                .memberType(Role.USER)
                .build();
        memberRepository.save(member);
        clear();
        //when
        MemberEntity findMember = memberRepository.findById(member.getMemberId())
                .orElseThrow(() -> new Exception());
        //then
        assertThat(findMember.getRegisterDate()).isNotNull();
        assertThat(findMember.getUpdateDate()).isNotNull();
    }
}