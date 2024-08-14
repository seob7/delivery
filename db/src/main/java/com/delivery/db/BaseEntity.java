package com.delivery.db;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@MappedSuperclass // Super class 이기 때문에 다른 테이블과 매핑 관계를 맺지 않고 서브 클래스에게 매핑 정보 제공
@Data
@SuperBuilder
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
