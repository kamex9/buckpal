package io.reflectoring.buckpal.adapter.out.persistence.account

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

/**
 * 口座を表す永続化エンティティクラス。
 *
 * @property id 口座ID
 * @property ownerLastName 契約者氏名（名）
 * @property ownerFirstName 契約者氏名（姓）
 * @property bankCode 銀行コード
 * @property branchNumber 支店番号
 * @property accountNumber 口座番号
 * @property createdAt 作成日時
 * @property updatedAt 更新日時
 */
@Table("account")
data class AccountEntity(
    @Id @Column("id") var id: Long = 0,
    @Column("owner_last_name") val ownerLastName: String,
    @Column("owner_first_name") val ownerFirstName: String,
    @Column("bank_code") val bankCode: Int,
    @Column("branch_number") val branchNumber: Int,
    @Column("account_number") val accountNumber: Int,
    @Column("created_at") val createdAt: LocalDateTime,
    @Column("updated_at") val updatedAt: LocalDateTime
)
