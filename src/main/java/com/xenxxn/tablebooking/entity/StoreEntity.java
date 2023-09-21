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
@Table(name = "store")
public class StoreEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long storeId;
    @Column(name = "store_name")
    private String storeName;
    private String addr;
    private String phone;
    private Double rating;
    @Column(name = "rating_count")
    private int ratingCount;
    @Column(name = "register_date")
    private LocalDateTime registerDate;
    @Column(name = "update_date")
    private LocalDateTime updateDate;
    @Column(name = "partner_id")
    private Long partnerId;

}
