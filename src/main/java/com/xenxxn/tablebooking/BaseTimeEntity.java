package com.xenxxn.tablebooking;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(name = "register_date", updatable = false)
    private LocalDateTime registerDate;

    @LastModifiedDate
    @Column(name = "update_date", updatable = true)
    private LocalDateTime updateDate;
}
