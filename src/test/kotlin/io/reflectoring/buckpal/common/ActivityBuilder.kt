package io.reflectoring.buckpal.common

import io.reflectoring.buckpal.application.domain.model.Account.AccountId
import io.reflectoring.buckpal.application.domain.model.Activity
import io.reflectoring.buckpal.application.domain.model.Activity.ActivityId
import io.reflectoring.buckpal.application.domain.model.Money
import java.time.LocalDateTime

class ActivityBuilder(
    private var id: ActivityId? = ActivityId(1L),
    private var ownerAccountId: AccountId = AccountId(42L),
    private var sourceAccountId: AccountId = AccountId(42L),
    private var targetAccountId: AccountId = AccountId(56L),
    private var createdAt: LocalDateTime = LocalDateTime.now(),
    private var money: Money = Money.of(999L)
) {
    fun withId(id: ActivityId?) = apply { this.id = id }
    fun withOwnerAccountId(accountId: AccountId) = apply { this.ownerAccountId = accountId }
    fun withSourceAccountId(accountId: AccountId) = apply { this.sourceAccountId = accountId }
    fun withTargetAccountId(accountId: AccountId) = apply { this.targetAccountId = accountId }
    fun withCreatedAt(createdAt: LocalDateTime) = apply { this.createdAt = createdAt }
    fun withMoney(money: Money) = apply { this.money = money }

    fun build(): Activity {
        return Activity(
            id,
            ownerAccountId,
            sourceAccountId,
            targetAccountId,
            createdAt,
            money
        )
    }
}
