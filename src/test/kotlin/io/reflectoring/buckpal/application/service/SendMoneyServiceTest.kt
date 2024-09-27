package io.reflectoring.buckpal.application.service

import io.mockk.every
import io.mockk.mockk
import io.reflectoring.buckpal.application.domain.model.Account
import io.reflectoring.buckpal.application.domain.model.Account.AccountId
import io.reflectoring.buckpal.application.domain.model.Money
import io.reflectoring.buckpal.application.port.`in`.sendmoney.*
import io.reflectoring.buckpal.application.port.out.account.LoadAccountPort
import io.reflectoring.buckpal.application.port.out.account.UpdateAccountStatePort
import io.reflectoring.buckpal.common.AccountBuilder
import io.reflectoring.buckpal.common.TestDoubleCreator
import io.reflectoring.buckpal.common.util.MessageUtils
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.time.LocalDate

class SendMoneyServiceTest {

    private lateinit var service: SendMoneyService
    private lateinit var dto: SendMoneyCommandDto
    private lateinit var loadAccountPort: LoadAccountPort
    private lateinit var updateAccountStatePort: UpdateAccountStatePort

    private val props = createSendMoneyPropertiesFake()
//    private val messageUtils = createMessageUtilsFake()

    companion object {
        private fun createSendMoneyPropertiesFake(): SendMoneyProperties {
            return SendMoneyProperties(1000L, LocalDate.of(2024,7,2))
        }

//        private fun createMessageUtilsFake(): MessageUtils {
//            return TestDoubleCreator.fakeMessageUtils(
//                null, mapOf(
//                    "send-money.threshold.exceeded" to "送金額がしきい値を超えています。: threshold={0}, actual={1}",
//                    "send-money.account.not-found" to "{0}口座が存在しません。: details={1}",
//                )
//            )
//        }

        private fun createLoadAccountPortStub(): LoadAccountPort {
            return object : LoadAccountPort {
                override fun loadAccount(accountId: AccountId, baselineDate: LocalDate): Account {
                    return AccountBuilder().build()
                }
            }
        }

        private fun createUpdateAccountStatePortStub(): UpdateAccountStatePort {
            return object : UpdateAccountStatePort {
                override fun updateActivities(account: Account) {
                    // 何もしない
                    return
                }
            }
        }

        private fun createCommandDto(amount: Long): SendMoneyCommandDto {
            return SendMoneyCommandDto(AccountId(42L), AccountId(56L), Money.of(amount))
        }
    }

    @BeforeEach
    fun setUp() {
        loadAccountPort = createLoadAccountPortStub()
        updateAccountStatePort = createUpdateAccountStatePortStub()
        service = SendMoneyService(props, loadAccountPort, updateAccountStatePort)
        dto = createCommandDto(150L)
    }

    @Test
    fun `test_sendMoney 上限しきい値以内であればOK`() {
        assertThat(service.sendMoney(dto)).isTrue()
    }

    @Test
    fun `test_sendMoney 上限しきい値超過であればNG`() {
        dto = createCommandDto(1001L)
        assertThatThrownBy { service.sendMoney(dto) }
            .isInstanceOf(ThresholdExceededException::class.java)
    }

    @Test
    fun `test_sendMoney 存在しない送金元であればNG`() {
        loadAccountPort = mockk()
        service = SendMoneyService(props, loadAccountPort, updateAccountStatePort)
        val sourceAccount = null
        val targetAccount = AccountBuilder().build()

        every { loadAccountPort.loadAccount(dto.sourceAccountId, any()) } returns sourceAccount
        every { loadAccountPort.loadAccount(dto.targetAccountId, any()) } returns targetAccount

        assertThatThrownBy { service.sendMoney(dto) }
            .isInstanceOf(SourceAccountNotFoundException::class.java)
    }

    @Test
    fun `test_sendMoney 存在しない送金先であればNG`() {
        loadAccountPort = mockk()
        service = SendMoneyService(props, loadAccountPort, updateAccountStatePort)
        val sourceAccount = AccountBuilder().build()
        val targetAccount = null

        every { loadAccountPort.loadAccount(dto.sourceAccountId, any()) } returns sourceAccount
        every { loadAccountPort.loadAccount(dto.targetAccountId, any()) } returns targetAccount

        assertThatThrownBy { service.sendMoney(dto) }
            .isInstanceOf(TargetAccountNotFoundException::class.java)
    }
}