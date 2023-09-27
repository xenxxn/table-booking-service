package com.xenxxn.tablebooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {
    private Long memberId;
    private String memberEmail;
    private String password;
    private String phone;
    private String memberType;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;

    public static Member fromEntity(com.xenxxn.tablebooking.entity.Member memberEntity) {
        return com.xenxxn.tablebooking.dto.Member.builder()
                .memberId(memberEntity.getId())
                .memberEmail(memberEntity.getUsername())
                .password(memberEntity.getPassword())
                .phone(memberEntity.getPhone())
                .memberType(memberEntity.getRole().name())
                .registerDate(memberEntity.getRegisterDate())
                .updateDate(memberEntity.getUpdateDate())
                .build();
    }
}
