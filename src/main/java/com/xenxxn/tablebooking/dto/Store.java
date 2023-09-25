package com.xenxxn.tablebooking.dto;

import com.xenxxn.tablebooking.entity.StoreEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Store {
    private Long storeId;
    private String storeName;
    private String addr;
    private String phone;
    private Double rating;
    private int ratingCount;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;
    private Long partnerId;

    public static Store fromEntity(StoreEntity storeEntity){
        return Store.builder()
                .storeId(storeEntity.getStoreId())
                .storeName(storeEntity.getStoreName())
                .addr(storeEntity.getAddr())
                .phone(storeEntity.getPhone())
                .rating(storeEntity.getRating())
                .ratingCount(storeEntity.getRatingCount())
                .registerDate(storeEntity.getRegisterDate())
                .updateDate(storeEntity.getUpdateDate())
                .partnerId(storeEntity.getPartnerId())
                .build();
    }
}
