package com.delivery.storeadmin.domain.authorization;

import com.delivery.db.storeuser.StoreUserEntity;
import com.delivery.storeadmin.domain.user.service.StoreUserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizationService implements UserDetailsService {

    private final StoreUserService storeUserService;

    /**
     * security default login page 에서 데이터를 입력하면 사용자를 찾고, 사용자가 없으면 예외를 시킨다.
     * 있다면, User 객체를 만들고 UserDetails 에서는 username, password 를 기초로 hash 하여 비교한다.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // UserDetails 반환
        UserDetails userDetails = storeUserService.getRegisterUser(username)
            .map(entity -> User.builder()
                .username(entity.getEmail())
                .password(entity.getPassword())
                .roles(entity.getRole().name())
                .build()).orElseThrow(() -> new UsernameNotFoundException(username));

        return userDetails;
    }

}
