package com.loopers.domain

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.time.ZonedDateTime

/**
 * 생성/수정/삭제 정보를 자동으로 관리해준다.
 * 재사용성을 위해 이 외의 컬럼이나 동작은 추가하지 않는다.
 *
 * @property id 엔티티 ID
 * @property createdAt 생성 시점
 * @property updatedAt 수정 시점
 * @property deletedAt 삭제 시점
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long = 0

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    open var createdAt: LocalDateTime = LocalDateTime.now()
        protected set

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    open var updatedAt: LocalDateTime = LocalDateTime.now()
        protected set

    @Column(name = "deleted_at")
    open var deletedAt: ZonedDateTime? = null
        protected set

    /**
     * delete 연산은 멱등하게 동작할 수 있도록 한다. (삭제된 엔티티를 다시 삭제해도 동일한 결과가 나오도록)
     */
    fun delete() {
        deletedAt ?: run { deletedAt = ZonedDateTime.now() }
    }

    /**
     * restore 연산은 멱등하게 동작할 수 있도록 한다. (삭제되지 않은 엔티티를 복원해도 동일한 결과가 나오도록)
     */
    fun restore() {
        deletedAt?.let { deletedAt = null }
    }
}
