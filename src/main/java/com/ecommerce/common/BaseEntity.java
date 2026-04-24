package com.ecommerce.common;

import jakarta.persistence.*;
import lombok.Getter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

	@CreatedDate
	@Column(updatable = false, nullable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(nullable = false)
	private LocalDateTime modifiedAt;

	private boolean deleted;
	private LocalDateTime deletedAt;

	/* 소프트 삭제 전략 공통 메서드 */
	public void softDelete() {
		this.deleted = true;
		this.deletedAt = LocalDateTime.now();
	}
}
