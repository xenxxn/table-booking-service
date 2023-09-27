package com.xenxxn.tablebooking.dto;

import com.xenxxn.tablebooking.entity.PartnerEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Partner {
    private Long partnerId;
    private String partnerEmail;
    private String password;
    private String phone;
    private String memberType;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;

    public static Partner fromEntity(PartnerEntity partnerEntity) {
        return Partner.builder()
                .partnerId(partnerEntity.getPartnerId())
                .partnerEmail(partnerEntity.getPartnerEmail())
                .password(partnerEntity.getPassword())
                .phone(partnerEntity.getPhone())
                .memberType(partnerEntity.getMemberType().name())
                .registerDate(partnerEntity.getRegisterDate())
                .updateDate(partnerEntity.getUpdateDate())
                .build();
    }
}
