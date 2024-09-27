package io.reflectoring.buckpal.application.domain.model

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.*

class MoneyTest {

    /**
     * Moneyオブジェクトの金額が0以上であるかどうか
     */
    @Test
    fun test_isPositiveOrZero() {
        val zero = Money.of(0L)
        assertThat(zero.isPositiveOrZero()).isTrue()
        val one = Money.of(1L)
        assertThat(one.isPositiveOrZero()).isTrue()
        val negative = Money.of(-1L)
        assertThat(negative.isPositiveOrZero()).isFalse()
    }

    /**
     * Moneyオブジェクトの金額が0未満であるかどうか
     */
    @Test
    fun test_isNegative() {
        val zero = Money.of(0L)
        assertThat(zero.isNegative()).isFalse()
        val one = Money.of(1L)
        assertThat(one.isNegative()).isFalse()
        val negative = Money.of(-1L)
        assertThat(negative.isNegative()).isTrue()
    }

    /**
     * Moneyオブジェクトの金額が0より大きいかどうか
     */
    @Test
    fun test_isPositive() {
        val zero = Money.of(0L)
        assertThat(zero.isPositive()).isFalse()
        val one = Money.of(1L)
        assertThat(one.isPositive()).isTrue()
        val negative = Money.of(-1L)
        assertThat(negative.isPositive()).isFalse()
    }

    /**
     * 現在のMoneyオブジェクトの金額が指定されたMoneyオブジェクトの金額以上であるか
     */
    @Test
    fun test_isGreaterThanOrEqualTo() {
        val money = Money.of(1L)
        val same = Money.of(1L)
        val greater = Money.of(2L)
        val smaller = Money.of(-1L)
        assertThat(money.isGreaterThanOrEqualTo(same)).isTrue()
        assertThat(money.isGreaterThanOrEqualTo(greater)).isFalse()
        assertThat(money.isGreaterThanOrEqualTo(smaller)).isTrue()
    }

    /**
     * 現在のMoneyオブジェクトの金額が指定されたMoneyオブジェクトの金額より大きいか
     */
    @Test
    fun test_isGreaterThan() {
        val money = Money.of(1L)
        val same = Money.of(1L)
        val greater = Money.of(2L)
        val smaller = Money.of(-1L)
        assertThat(money.isGreaterThan(same)).isFalse()
        assertThat(money.isGreaterThan(greater)).isFalse()
        assertThat(money.isGreaterThan(smaller)).isTrue()
    }

    /**
     * 指定されたMoneyオブジェクトの金額を減算し、新しいMoneyオブジェクトを返すか
     */
    @Test
    fun test_minus() {
        val base = Money.of(3L)
        val value = Money.of(4L)
        assertThat(base.minus(value).amount).isEqualTo(-1L)
    }

    /**
     * 指定されたMoneyオブジェクトの金額を加算し、新しいMoneyオブジェクトを返すか
     */
    @Test
    fun test_plus() {
        val base = Money.of(-1L)
        val value = Money.of(4L)
        assertThat(base.plus(value).amount).isEqualTo(3L)
    }

    /**
     * 金額を正負反転させた新しいMoneyオブジェクトを返すか
     */
    @Test
    fun test_negate() {
        val money = Money.of(-3L)
        assertThat(money.negate().amount).isEqualTo(3L)
        val zero = Money.of(0L)
        assertThat(zero.negate().amount).isEqualTo(0L)
    }
}