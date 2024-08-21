package com.delivery.storeadmin.domain.userordermenu.service;

import com.delivery.db.userordermenu.UserOrderMenuEntity;
import com.delivery.db.userordermenu.UserOrderMenuRepository;
import com.delivery.db.userordermenu.enums.UserOrderMenuStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserOrderMenuService {

    private final UserOrderMenuRepository userOrderMenuRepository;

    public List<UserOrderMenuEntity> getUserOrderMenuList(Long userOrderId) {
        return userOrderMenuRepository.findAllByUserOrderIdAndStatus(userOrderId,
            UserOrderMenuStatus.REGISTERED);
    }


}
