package io.reflectoring.buckpal.common

import io.reflectoring.buckpal.application.domain.model.Account
import io.reflectoring.buckpal.application.domain.model.Account.AccountId
import io.reflectoring.buckpal.application.domain.model.ActivityHistory
import io.reflectoring.buckpal.application.domain.model.Money

class AccountBuilder(
    private var id: AccountId = AccountId(42L),
    private var baselineBalance: Money = Money.of(999L),
    private var activityHistory: ActivityHistory = ActivityHistory.of(
        ActivityBuilder().build(),
        ActivityBuilder().build()
    )
) {
    fun withId(id: AccountId) = apply { this.id = id }
    fun withBaselineBalance(baselineBalance: Money) = apply { this.baselineBalance = baselineBalance }
    fun withActivityHistory(activityHistory: ActivityHistory) = apply { this.activityHistory = activityHistory }

    fun build(): Account {
        return Account(id, baselineBalance, activityHistory)
    }
}
