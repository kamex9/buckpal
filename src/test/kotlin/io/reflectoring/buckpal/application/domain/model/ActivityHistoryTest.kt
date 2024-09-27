package io.reflectoring.buckpal.application.domain.model


import io.reflectoring.buckpal.common.ActivityBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ActivityHistoryTest {

    @Test
    fun test_calculateNetBalance() {
        // Arrange
        val account1 = Account.AccountId(49L)
        val account2 = Account.AccountId(56L)
        val activities = mutableListOf(
            ActivityBuilder()
                .withSourceAccountId(account1)
                .withTargetAccountId(account2)
                .withMoney(Money.of(140L))
                .build(),
            ActivityBuilder()
                .withSourceAccountId(account2)
                .withTargetAccountId(account1)
                .withMoney(Money.of(350L))
                .build(),
            ActivityBuilder()
                .withSourceAccountId(account1)
                .withTargetAccountId(account2)
                .withMoney(Money.of(630L))
                .build()
        )
        val activityHistory = ActivityHistory(activities)

        // Act
        val actualAccount1Balance = activityHistory.calculateNetBalance(account1)
        val actualAccount2Balance = activityHistory.calculateNetBalance(account2)

        // Assert
        assertThat(actualAccount1Balance).isEqualTo(Money.of(-420L))
        assertThat(actualAccount2Balance).isEqualTo(Money.of(420L))
    }
}