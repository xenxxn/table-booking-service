package com.xenxxn.tablebooking.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "reservation")
public class ReservationEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;
    private String status;
    @Column(name = "reserve_date")
    private LocalDateTime reserveDate;
    @Column(name = "store_id")
    private Long storeId;
    @Column(name = "member_id")
    private Long memberId;
    private Long headcount;
}
