package com.delivery.api.config.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.delivery.db") // org.delivery.db 하위의 Entity 를 모두 Scan
@EnableJpaRepositories(basePackages = "com.delivery.db") // org.delivery.db 하위의 Repository 모두 가져옴
public class JpaConfig {

}
