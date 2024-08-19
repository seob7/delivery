package com.delivery.db.userordermenu;

import com.delivery.db.userordermenu.enums.UserOrderMenuStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOrderMenuRepository extends JpaRepository<UserOrderMenuEntity, Long> {

    List<UserOrderMenuEntity> findAllByUserOrderIdAndStatus(Long userOrderId,
        UserOrderMenuStatus status);

}
