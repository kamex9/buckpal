package io.reflectoring.buckpal.application.domain.model

import java.math.BigInteger

/**
 * 金額を表す値オブジェクトクラス。
 *
 * @property amount 金額を保持するBigInteger
 */
@JvmInline
value class Money(val amount: BigInteger) {

    /**
     * 金額が正またはゼロかどうかを判定します。
     *
     * @return 金額が正またはゼロの場合はtrue、それ以外の場合はfalse
     */
    fun isPositiveOrZero(): Boolean {
        return this.amount >= ZERO.amount
    }

    /**
     * 金額が負かどうかを判定します。
     *
     * @return 金額が負の場合はtrue、それ以外の場合はfalse
     */
    fun isNegative(): Boolean {
        return this.amount < ZERO.amount
    }

    /**
     * 金額が正かどうかを判定します。
     *
     * @return 金額が正の場合はtrue、それ以外の場合はfalse
     */
    fun isPositive(): Boolean {
        return this.amount > ZERO.amount
    }

    /**
     * 指定された金額以上かどうかを判定します。
     *
     * @param money 比較対象のMoneyオブジェクト
     * @return 指定された金額以上の場合はtrue、それ以外の場合はfalse
     */
    fun isGreaterThanOrEqualTo(money: Money): Boolean {
        return this.amount >= money.amount
    }

    /**
     * 指定された金額より大きいかどうかを判定します。
     *
     * @param money 比較対象のMoneyオブジェクト
     * @return 指定された金額より大きい場合はtrue、それ以外の場合はfalse
     */
    fun isGreaterThan(money: Money): Boolean {
        return this.amount > money.amount
    }

    /**
     * 指定された金額を減算した新しいMoneyオブジェクトを返します。
     *
     * @param money 減算する金額のMoneyオブジェクト
     * @return 減算結果の新しいMoneyオブジェクト
     */
    fun minus(money: Money): Money {
        val amount = this.amount - money.amount
        return Money(amount)
    }

    /**
     * 指定された金額を加算した新しいMoneyオブジェクトを返します。
     *
     * @param money 加算する金額のMoneyオブジェクト
     * @return 加算結果の新しいMoneyオブジェクト
     */
    fun plus(money: Money): Money {
        val amount = this.amount + money.amount
        return Money(amount)
    }

    /**
     * 現在のMoneyオブジェクトの金額を正負反転させた新しいMoneyオブジェクトを返します。
     *
     * @return 金額を正負反転させた新しいMoneyオブジェクト
     */
    fun negate(): Money {
        return Money(this.amount.negate())
    }

    companion object {

        /** 0円を表現するシングルトン */
        val ZERO = Money(BigInteger.ZERO)

        /**
         * 指定された値を持つ新しいMoneyオブジェクトを生成します。
         *
         * @param value 金額の値
         * @return 新しいMoneyオブジェクト
         */
        fun of(value: Long): Money {
            return Money(BigInteger.valueOf(value))
        }

        /**
         * 2つのMoneyオブジェクトの金額を加算した新しいMoneyオブジェクトを返します。
         *
         * @param a 加算する最初のMoneyオブジェクト
         * @param b 加算する2つ目のMoneyオブジェクト
         * @return 加算結果の新しいMoneyオブジェクト
         */
        fun add(a: Money, b: Money): Money {
            return Money(a.amount.add(b.amount))
        }

        /**
         * 2つのMoneyオブジェクトの金額を減算した新しいMoneyオブジェクトを返します。
         *
         * @param a 減算されるMoneyオブジェクト
         * @param b 減算するMoneyオブジェクト
         * @return 減算結果の新しいMoneyオブジェクト
         */
        fun subtract(a: Money, b: Money): Money {
            return Money(a.amount.subtract(b.amount))
        }
    }
}
