package com.xenxxn.tablebooking.service;

import com.xenxxn.tablebooking.entity.MemberEntity;
import com.xenxxn.tablebooking.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberEntity member = memberRepository.findByMemberEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("아이디가 없습니다"));

        return User.builder()
                .username(member.getMemberEmail())
                .password(member.getPassword())
                .roles(member.getMemberType().name())
                .build();
    }
}
