package com.xenxxn.tablebooking.repository;

import com.xenxxn.tablebooking.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUsername(String username);
    Optional<Member> findByUsername(String username);
    Optional<Member> findByRefreshToken(String refreshToken);
}
