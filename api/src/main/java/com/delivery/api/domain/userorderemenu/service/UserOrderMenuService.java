package com.delivery.api.domain.userorderemenu.service;

import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.db.userordermenu.UserOrderMenuEntity;
import com.delivery.db.userordermenu.UserOrderMenuRepository;
import com.delivery.db.userordermenu.enums.UserOrderMenuStatus;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserOrderMenuService {

    private final UserOrderMenuRepository userOrderMenuRepository;

    public List<UserOrderMenuEntity> getUserOrderMenu(Long userOrderId) {
       return  userOrderMenuRepository.findAllByUserOrderIdAndStatus(userOrderId,
            UserOrderMenuStatus.REGISTERED);
    }

    public UserOrderMenuEntity order(UserOrderMenuEntity userOrderMenuEntity) {
        return Optional.ofNullable(userOrderMenuEntity).map(entity -> {
            entity.setStatus(UserOrderMenuStatus.REGISTERED);
            return userOrderMenuRepository.save(entity);
        }).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

}
