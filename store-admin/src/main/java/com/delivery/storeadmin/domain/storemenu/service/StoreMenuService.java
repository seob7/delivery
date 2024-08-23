package com.delivery.storeadmin.domain.storemenu.service;

import com.delivery.db.storemenu.StoreMenuEntity;
import com.delivery.db.storemenu.StoreMenuRepository;
import com.delivery.db.storemenu.enums.StoreMenuStatus;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreMenuService {

    private final StoreMenuRepository storeMenuRepository;

    public StoreMenuEntity getStoreMenuWithThrow(Long id) {
        return Optional.ofNullable(storeMenuRepository.findFirstByIdAndStatusOrderByIdDesc(id,
                StoreMenuStatus.REGISTERED))
            .orElseThrow(() -> new RuntimeException("Store Menu Not Found"));
    }


}
