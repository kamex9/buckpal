package io.reflectoring.buckpal.adapter.out.persistence.account

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

/**
 * 口座の取引を表す永続化エンティティクラス。
 *
 * @property id 取引ID
 * @property ownerAccountId 所有者アカウントID
 * @property sourceAccountId 送金元アカウントID
 * @property targetAccountId 送金先アカウントID
 * @property createdAt 取引のタイムスタンプ
 * @property amount 金額
 */
@Table("activity")
data class ActivityEntity(
    @Id @Column("id") var id: Long = 0,
    @Column("owner_account_id") val ownerAccountId: Long,
    @Column("source_account_id") val sourceAccountId: Long,
    @Column("target_account_id") val targetAccountId: Long,
    @Column("created_at") val createdAt: LocalDateTime,
    @Column("amount") val amount: Long
)
