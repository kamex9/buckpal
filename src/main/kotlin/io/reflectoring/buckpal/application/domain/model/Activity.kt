package io.reflectoring.buckpal.application.domain.model

import java.time.LocalDateTime

/**
 * アカウントの取引を表すデータクラス。
 *
 * @property id 取引ID
 * @property ownerAccountId 所有者アカウントID
 * @property sourceAccountId 送金元アカウントID
 * @property targetAccountId 送金先アカウントID
 * @property createdAt 取引のタイムスタンプ
 * @property money 金額
 */
data class Activity(
    val id: ActivityId? = null,
    val ownerAccountId: Account.AccountId,
    val sourceAccountId: Account.AccountId,
    val targetAccountId: Account.AccountId,
    val createdAt: LocalDateTime,
    val money: Money
) {
    /**
     * 取引IDを表すインラインクラス。
     *
     * @property value 取引IDの値
     */
    @JvmInline
    value class ActivityId(val value: Long)
}
