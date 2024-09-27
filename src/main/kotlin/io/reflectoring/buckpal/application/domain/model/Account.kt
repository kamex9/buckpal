package io.reflectoring.buckpal.application.domain.model

import java.time.LocalDateTime

/**
 * 口座を表すデータクラス。
 *
 * @property id 口座ID
 * @property baselineBalance ある時点の口座残高
 * @property activityHistory この口座に関わる入出金取引履歴
 */
data class Account(
    val id: AccountId = AccountId(-1L),
    val baselineBalance: Money = Money.of(0L),
    val activityHistory: ActivityHistory = ActivityHistory.ofEmpty()
) {

    /**
     * 基準日以降の入出金履歴を加味した現時点の口座残高を返却します。
     *
     * @return 口座残高
     */
    fun getCurrentBalance(): Money {
        return Money.add(baselineBalance, activityHistory.calculateNetBalance(id))
    }

    /**
     * 入金操作を実行します。
     *
     * @param money 金額
     * @param sourceAccountId 送金元口座ID
     */
    fun deposit(money: Money, sourceAccountId: AccountId): Boolean {
        val newActivity = Activity(
            ownerAccountId = id,
            sourceAccountId = sourceAccountId,
            targetAccountId = id,
            createdAt = LocalDateTime.now(),
            money = money
        )
        activityHistory.addActivity(newActivity)
        return true
    }

    /**
     * 出金操作を実行します。
     *
     * @param money 金額
     * @param targetAccountId 送金先口座ID
     */
    fun withdraw(money: Money, targetAccountId: AccountId): Boolean {
        if (cannotWithDraw(money)) {
            return false
        }

        val newActivity = Activity(
            ownerAccountId = id,
            sourceAccountId = id,
            targetAccountId = targetAccountId,
            createdAt = LocalDateTime.now(),
            money = money
        )
        activityHistory.addActivity(newActivity)
        return true
    }

    private fun cannotWithDraw(money: Money): Boolean {
        return money.isGreaterThan(getCurrentBalance())
    }

    /**
     * 口座IDを表すバリュークラス。
     *
     * @property value 口座ID
     */
    @JvmInline
    value class AccountId(val value: Long)
}
