package com.delivery.db.account;

import com.delivery.db.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder // 슈퍼 클래스의 필드까지 Builder 에 포함
@Data
@EqualsAndHashCode(callSuper = true) // 객체 비교시 사용, true 일 때 슈퍼 클래스인 BaseEntity 와 비교
@Entity
@Table(name = "account")
public class AccountEntity extends BaseEntity {

}
