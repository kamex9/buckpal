package io.reflectoring.buckpal.application.port.out.account

import io.reflectoring.buckpal.application.domain.model.Account

/**
 * アカウントの状態を更新するためのポートインターフェース。
 */
interface UpdateAccountStatePort {

    /**
     * アカウントのアクティビティを更新します。
     *
     * @param account 更新対象のアカウント
     */
    fun updateActivities(account: Account)
}