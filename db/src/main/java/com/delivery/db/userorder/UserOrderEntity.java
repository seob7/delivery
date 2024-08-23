package com.delivery.db.userorder;

import com.delivery.db.BaseEntity;
import com.delivery.db.store.StoreEntity;
import com.delivery.db.userorder.enums.UserOrderStatus;
import com.delivery.db.userordermenu.UserOrderMenuEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "user_order")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserOrderEntity extends BaseEntity {

    @Column(nullable = false)
    private Long userId;

    @JoinColumn(nullable = false, name = "store_id")
    @ManyToOne
    private StoreEntity store;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private UserOrderStatus status;

    @Column(precision = 11, scale = 4, nullable = false)
    private BigDecimal amount;

    private LocalDateTime orderedAt;

    private LocalDateTime acceptedAt;

    private LocalDateTime cookingStoredAt;

    private LocalDateTime deliveryStartedAt;

    private LocalDateTime receivedAt;

    @OneToMany(mappedBy = "userOrder")
    @ToString.Exclude
    @JsonIgnore
    private List<UserOrderMenuEntity> userOrderMenuList;

}
