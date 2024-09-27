package io.reflectoring.buckpal.adapter.out.persistence.account

import io.reflectoring.buckpal.application.domain.model.Account
import io.reflectoring.buckpal.application.domain.model.Account.AccountId
import io.reflectoring.buckpal.application.port.out.account.LoadAccountPort
import io.reflectoring.buckpal.application.port.out.account.UpdateAccountStatePort
import org.springframework.stereotype.Component
import java.time.LocalDate

/**
 * 口座データ永続化アダプタクラス。
 */
@Component
class AccountPersistenceAdapter(
    private val accountRepository: AccountRepository,
    private val activityRepository: ActivityRepository
) : LoadAccountPort, UpdateAccountStatePort {
    override fun loadAccount(accountId: AccountId, baselineDate: LocalDate): Account? {
        // AccountのDB取得
        val existingAccount = accountRepository.findById(accountId.value).orElse(null) ?: return null

        // Activityの一覧をDB取得。baselineDate以降のデータ。
        val activities = activityRepository.findByOwnerSince(accountId.value, baselineDate)

        // baselineDate以前のActivityの一覧をDB取得。source or target両方。
        // 一覧から入金合計金額と出金金額合計を算出
        val totalDepositsUntilBaselineDate = activityRepository.getTotalDepositsUntil(accountId.value, baselineDate)
        val totalWithdrawalsUntilBaselineDate = activityRepository.getTotalWithdrawalsUntil(accountId.value, baselineDate)

        // Accountの生成返却
        return AccountMapper.mapToAccount(
            accountEntity = existingAccount,
            activityEntities = activities,
            totalDeposits = totalDepositsUntilBaselineDate,
            totalWithdrawals = totalWithdrawalsUntilBaselineDate
        )
    }

    override fun updateActivities(account: Account) {
        account.activityHistory.getActivities()
            .forEach { println("AccountPersistenceAdapter#updateActivities it=${it}") }

        account.activityHistory.getActivities()
            .filter { it.id == null }
            .forEach { activityRepository.save(AccountMapper.mapToActivityEntity(it)) }
    }

}