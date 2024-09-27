package io.reflectoring.buckpal.application.domain.model

import io.reflectoring.buckpal.common.AccountBuilder
import io.reflectoring.buckpal.common.ActivityBuilder
import io.reflectoring.buckpal.application.domain.model.Account.AccountId
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AccountTest {

    private lateinit var account: Account
    private val thisAccountId = AccountId(1L)

    @BeforeEach
    fun setUp() {
        account = AccountBuilder()
            .withId(thisAccountId)
            .withBaselineBalance(Money.of(2100L))
            .withActivityHistory(
                ActivityHistory.of(
                    ActivityBuilder()
                        .withSourceAccountId(thisAccountId)
                        .withMoney(Money.of(700L))
                        .build(),
                    ActivityBuilder()
                        .withTargetAccountId(thisAccountId)
                        .withMoney(Money.of(3400L))
                        .build(),
                )
            )
            .build()

    }

    @Test
    fun test_deposit() {
        // Given
        val money = Money.of(2500L)
        val sourceAccountId = AccountId(99L)

        // When
        val result = account.deposit(money, sourceAccountId)
        val resultActivities = account.activityHistory.getActivities()

        // Then
        assertThat(result).isTrue()
        assertThat(resultActivities).hasSize(3)
        assertThat(resultActivities.last().sourceAccountId).isEqualTo(sourceAccountId)
        assertThat(resultActivities.last().targetAccountId).isEqualTo(thisAccountId)
        assertThat(resultActivities.last().money).isEqualTo(money)
    }

    @Test
    fun test_withdraw() {
        // Given
        val money = Money.of(2500L)
        val targetAccountId = AccountId(99L)

        // When
        val result = account.withdraw(money, targetAccountId)
        val resultActivities = account.activityHistory.getActivities()

        // Then
        assertThat(result).isTrue()
        assertThat(resultActivities).hasSize(3)
        assertThat(resultActivities.last().sourceAccountId).isEqualTo(thisAccountId)
        assertThat(resultActivities.last().targetAccountId).isEqualTo(targetAccountId)
        assertThat(resultActivities.last().money).isEqualTo(money)
    }

    @Test
    fun `test_withdraw 残高オーバーはNG`() {
        val money = Money.of(450000L)
        val targetAccountId = AccountId(99L)

        assertThat(account.withdraw(money, targetAccountId)).isFalse()
    }

    @Test
    fun test_getCurrentBalance() {
        assertThat(account.getCurrentBalance()).isEqualTo(Money.of(2100L - 700L + 3400L))
    }


}