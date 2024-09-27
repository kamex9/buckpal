package io.reflectoring.buckpal.application.port.`in`.sendmoney

import io.reflectoring.buckpal.application.domain.model.Account
import io.reflectoring.buckpal.application.domain.model.Money
import jakarta.validation.ConstraintViolationException
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.*

class SendMoneyCommandDtoTest {

    @Test
    fun `test_validation 正常系`() {
        assertThatCode {
            SendMoneyCommandDto(
                Account.AccountId(42L),
                Account.AccountId(43L),
                Money.of(100L)
            )
        }.doesNotThrowAnyException()
    }

    @Test
    fun `test_validation 金額が負の値なので不正`() {
        assertThatThrownBy {
            SendMoneyCommandDto(
                Account.AccountId(42L),
                Account.AccountId(43L),
                Money.of(-10L)
            )
        }.isInstanceOf(ConstraintViolationException::class.java)
    }
}