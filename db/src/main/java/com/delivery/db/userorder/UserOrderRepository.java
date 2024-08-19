package com.delivery.db.userorder;

import com.delivery.db.userorder.enums.UserOrderStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOrderRepository extends JpaRepository<UserOrderEntity, Long> {

    // 특정 유저의 모든 주문
    List<UserOrderEntity> findAllByUserIdAndStatusOrderByIdDesc(Long userId,
        UserOrderStatus status);

    List<UserOrderEntity> findAllByUserIdAndStatusInOrderByIdDesc(Long userId,
        List<UserOrderStatus> status);


    // 특정 주문
    Optional<UserOrderEntity> findAllByIdAndStatusAndUserId(Long id, UserOrderStatus status,
        Long userId);

    Optional<UserOrderEntity> findAllByIdAndUserId(Long id, Long userId);
}
