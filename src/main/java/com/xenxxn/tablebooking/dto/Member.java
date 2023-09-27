package com.xenxxn.tablebooking.dto;

import com.xenxxn.tablebooking.entity.MemberEntity;
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

//    public static Member fromEntity(MemberEntity memberEntity) {
//        return Member.builder()
//                .memberId(memberEntity.getMemberId())
//                .memberEmail(memberEntity.getMemberEmail())
//                .password(memberEntity.getPassword())
//                .phone(memberEntity.getPhone())
//                .memberType(memberEntity.ge)
//                .registerDate(memberEntity.getRegisterDate())
//                .updateDate(memberEntity.getUpdateDate())
//                .build();
//    }
}
