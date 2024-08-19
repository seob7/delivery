package com.delivery.storeadmin.domain.authorization;

import com.delivery.db.store.StoreEntity;
import com.delivery.db.store.StoreRepository;
import com.delivery.db.store.enums.StoreStatus;
import com.delivery.db.storeuser.StoreUserEntity;
import com.delivery.storeadmin.domain.authorization.model.UserSession;
import com.delivery.storeadmin.domain.user.service.StoreUserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizationService implements UserDetailsService {

    private final StoreUserService storeUserService;
    private final StoreRepository storeRepository;


    /**
     * security default login page 에서 데이터를 입력하면 사용자를 찾고, 사용자가 없으면 예외를 시킨다. 있다면, User 객체를 만들고
     * UserDetails 에서는 username, password 를 기초로 hash 하여 비교한다.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<StoreUserEntity> storeUserEntity = storeUserService.getRegisterUser(username);
        Optional<StoreEntity> storeEntity = storeRepository.findFirstByIdAndStatusOrderByIdDesc(
            storeUserEntity.get().getStoreId(), StoreStatus.REGISTERED);

        // UserDetails 반환
        return storeUserEntity.map(entity ->
            UserSession.builder()
                .userId(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .status(entity.getStatus())
                .role(entity.getRole())
                .registeredAt(entity.getRegisteredAt())
                .lastLoginAt(entity.getLastLoginAt())
                .unregisteredAt(entity.getUnregisteredAt())

                .storeId(storeEntity.get().getId())
                .storeName(storeEntity.get().getName())
                .build()
        ).orElseThrow(() -> new UsernameNotFoundException(username));
    }

}
