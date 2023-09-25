package com.xenxxn.tablebooking.repository;

import com.xenxxn.tablebooking.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    boolean existsByMemberEmail(String memberEmail);
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
}
