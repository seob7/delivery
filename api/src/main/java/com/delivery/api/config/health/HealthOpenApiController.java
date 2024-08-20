package com.delivery.api.config.health;

import com.delivery.api.common.rabbitmq.Producer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/open-api")
public class HealthOpenApiController {

    private final Producer producer;

    @GetMapping("/health")
    public void health() {
        log.info("health call");
    }

}
