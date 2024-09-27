package io.reflectoring.buckpal.application.port.out.account

import io.reflectoring.buckpal.application.domain.model.Account
import io.reflectoring.buckpal.application.domain.model.Account.AccountId
import java.time.LocalDate

/**
 * アカウント情報を読み込むためのポート。
 */
interface LoadAccountPort {

    /**
     * 指定されたアカウントIDと基準日を基にアカウント情報を読み込みます。
     *
     * @param accountId アカウントID
     * @param baselineDate 基準日
     * @return 読み取ったアカウント情報
     */
    fun loadAccount(accountId: AccountId, baselineDate: LocalDate): Account?
}
