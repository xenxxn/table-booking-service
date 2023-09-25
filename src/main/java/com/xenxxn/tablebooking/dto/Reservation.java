package com.xenxxn.tablebooking.dto;

import com.xenxxn.tablebooking.entity.ReservationEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    private Long reservationId;
    private String status;
    private LocalDateTime reserveDate;
    private Long storeId;
    private Long memberId;
    private Long headcount;

    public static Reservation fromEntity(ReservationEntity reservationEntity) {
        return Reservation.builder()
                .reservationId(reservationEntity.getReservationId())
                .status(reservationEntity.getStatus())
                .reserveDate(reservationEntity.getReserveDate())
                .storeId(reservationEntity.getStoreId())
                .memberId(reservationEntity.getMemberId())
                .headcount(reservationEntity.getHeadcount())
                .build();
    }
}
