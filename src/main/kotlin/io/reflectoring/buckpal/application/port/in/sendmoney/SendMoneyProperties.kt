package io.reflectoring.buckpal.application.port.`in`.sendmoney

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.time.LocalDate

/**
 * 口座間送金操作プロパティクラス。
 *
 * @property maxTransferThreshold 許容する最大金額しきい値
 * @property baselineDate 取引処理基準日
 */
@Component
@ConfigurationProperties(prefix = "buckpal.send-money")
data class SendMoneyProperties(
    var maxTransferThreshold: Long = 10000,
    var baselineDate: LocalDate = LocalDate.of(2024,1,1)
)
