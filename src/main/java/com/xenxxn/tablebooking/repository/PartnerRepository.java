package com.xenxxn.tablebooking.repository;

import com.xenxxn.tablebooking.entity.PartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<PartnerEntity, Long> {
}
