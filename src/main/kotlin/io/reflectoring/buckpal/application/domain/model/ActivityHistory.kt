package io.reflectoring.buckpal.application.domain.model

/**
 * 口座取引の履歴を表すクラス。
 *
 * @property activities この履歴内の活動のリスト
 */
class ActivityHistory(private val activities: MutableList<Activity>) {

    /**
     * 指定されたアカウントIDに対する入金と出金の純合計を計算します。
     *
     * @param accountId アカウントID
     * @return 入出金合計
     */
    fun calculateNetBalance(accountId: Account.AccountId): Money {
        // 指定口座に向けた送金取引（指定口座の入金）
        val totalDeposits = activities.filter { it.targetAccountId == accountId }
            .map { it.money }
            .fold(Money.ZERO, Money::add)

        // 指定口座からの送金取引（指定口座の出金）
        val totalWithdrawals = activities.filter { it.sourceAccountId == accountId }
            .map { it.money }
            .fold(Money.ZERO, Money::add)

        return Money.add(totalDeposits, totalWithdrawals.negate())
    }

    /**
     * 新しい取引をこの履歴に追加します。
     *
     * @param activity 追加する取引
     */
    fun addActivity(activity: Activity) {
        activities.add(activity)
    }

    /**
    * 取引履歴を返却します。
    *
    * @return イミュータブルな取引履歴
    */
    fun getActivities(): List<Activity> {
        return activities.toList()
    }

    override fun toString(): String {
        return activities.joinToString(separator = ", ", prefix = "ActivityHistory(", postfix = ")") { activity ->
            activity.toString()
        }
    }

    companion object {
        /**
         * 複数の取引からなる履歴を作成します。
         *
         * @param activities 取引の可変引数
         * @return 取引履歴
         */
        fun of(vararg activities: Activity): ActivityHistory {
            return ActivityHistory(activities.toMutableList())
        }

        /**
         * 空の取引履歴を返却します。
         */
        fun ofEmpty(): ActivityHistory {
            return ActivityHistory(mutableListOf())
        }
    }
}