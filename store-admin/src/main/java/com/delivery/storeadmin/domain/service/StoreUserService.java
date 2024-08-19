package com.delivery.storeadmin.domain.service;

import com.delivery.db.storeuser.StoreUserEntity;
import com.delivery.db.storeuser.StoreUserRepository;
import com.delivery.db.storeuser.enums.StoreUserStatus;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreUserService {

    private final StoreUserRepository storeUserRepository;
    private final PasswordEncoder passwordEncoder;

    public StoreUserEntity register(StoreUserEntity storeUserEntity) {
        storeUserEntity.setStatus(StoreUserStatus.REGISTERED);
        storeUserEntity.setPassword(passwordEncoder.encode(storeUserEntity.getPassword()));
        storeUserEntity.setRegisteredAt(LocalDateTime.now());
        return storeUserRepository.save(storeUserEntity);
    }

    public Optional<StoreUserEntity> getRegisterUser(String email) {
        return storeUserRepository.findFirstByEmailAndStatusOrderByIdDesc(email,
            StoreUserStatus.REGISTERED);
    }
}
