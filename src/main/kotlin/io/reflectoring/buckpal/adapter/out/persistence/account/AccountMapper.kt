package io.reflectoring.buckpal.adapter.out.persistence.account

import io.reflectoring.buckpal.application.domain.model.Account
import io.reflectoring.buckpal.application.domain.model.Account.AccountId
import io.reflectoring.buckpal.application.domain.model.Activity
import io.reflectoring.buckpal.application.domain.model.ActivityHistory
import io.reflectoring.buckpal.application.domain.model.Money

/**
 * 口座関連データについて永続化エンティティとドメインモデル間の変換を行うクラス。
 */
object AccountMapper {

    /**
     * 口座データについて、永続化エンティティからドメインモデルを生成して返却します。
     *
     * @param accountEntity 口座エンティティ
     * @param totalDeposits 合計入金金額
     * @param totalWithdrawals 合計出金金額
     * @param activityEntities 取引エンティティ
     * @return 口座ドメインモデル
     */
    fun mapToAccount(
        accountEntity: AccountEntity,
        totalDeposits: Long,
        totalWithdrawals: Long,
        activityEntities: List<ActivityEntity>
    ): Account {
        val baselineBalance = Money.of(totalDeposits - totalWithdrawals)
        val activities = activityEntities.map {
            Activity(
                id = Activity.ActivityId(it.id),
                ownerAccountId = AccountId(it.ownerAccountId),
                sourceAccountId = AccountId(it.sourceAccountId),
                targetAccountId = AccountId(it.targetAccountId),
                createdAt = it.createdAt,
                money = Money.of(it.amount)
            )
        }
        return Account(
            id = AccountId(accountEntity.id),
            baselineBalance = baselineBalance,
            activityHistory = ActivityHistory(activities.toMutableList())
        )
    }

    /**
     * 取引データについて、ドメインモデルから永続化エンティティを生成して返却します。
     *
     * @param activity 取引ドメインモデル
     * @return 取引エンティティ
     */
    fun mapToActivityEntity(activity: Activity): ActivityEntity {
        return ActivityEntity(
            id = activity.id?.value ?: 0L,
            ownerAccountId = activity.ownerAccountId.value,
            sourceAccountId = activity.sourceAccountId.value,
            targetAccountId = activity.targetAccountId.value,
            createdAt = activity.createdAt,
            amount = activity.money.amount.toLong()
        )
    }
}