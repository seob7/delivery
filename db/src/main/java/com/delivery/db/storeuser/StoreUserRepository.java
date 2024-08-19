package com.delivery.db.storeuser;

import com.delivery.db.storeuser.enums.StoreUserStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreUserRepository extends JpaRepository<StoreUserEntity, Long> {

    Optional<StoreUserEntity> findFirstByEmailAndStatusOrderByIdDesc(String email,
        StoreUserStatus status);

}
