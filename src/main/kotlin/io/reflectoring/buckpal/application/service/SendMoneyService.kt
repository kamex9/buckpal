package io.reflectoring.buckpal.application.service

import io.reflectoring.buckpal.application.domain.model.Money
import io.reflectoring.buckpal.application.port.`in`.sendmoney.*
import io.reflectoring.buckpal.application.port.out.account.LoadAccountPort
import io.reflectoring.buckpal.application.port.out.account.UpdateAccountStatePort
import org.springframework.stereotype.Service

/**
 * 口座間送金操作ユースケースの実装サービスクラス。
 *
 * @property props 口座間送金操作プロパティ
 * @property loadAccountPort 口座情報読み込みポート
 * @property updateAccountStatePort 口座情報更新ポート
 */
@Service
class SendMoneyService(
    private val props: SendMoneyProperties,
    private val loadAccountPort: LoadAccountPort,
    private val updateAccountStatePort: UpdateAccountStatePort
) : SendMoneyUseCase {

    /**
     * 送金を実行します。
     *
     * @param dto 送金コマンド
     * @return 送金が成功した場合はtrue、失敗した場合はfalse
     */
    override fun sendMoney(dto: SendMoneyCommandDto): Boolean {
        val threshold = Money.of(props.maxTransferThreshold)
        val money = dto.money
        money.takeIf { it.isGreaterThan(threshold) }?.let { throw ThresholdExceededException(threshold, it) }

        val baselineDate = props.baselineDate
        val sourceAccountId = dto.sourceAccountId
        val targetAccountId = dto.targetAccountId

        val sourceAccount = loadAccountPort.loadAccount(sourceAccountId, baselineDate)
            ?: throw SourceAccountNotFoundException(sourceAccountId, dto)
        val targetAccount = loadAccountPort.loadAccount(targetAccountId, baselineDate)
            ?: throw TargetAccountNotFoundException(targetAccountId, dto)

        sourceAccount.withdraw(money, targetAccountId)
        targetAccount.deposit(money, sourceAccountId)

        updateAccountStatePort.updateActivities(sourceAccount)
        updateAccountStatePort.updateActivities(targetAccount)

        return true
    }
}