package io.reflectoring.buckpal.application.port.`in`.sendmoney

import io.reflectoring.buckpal.application.domain.model.Account.AccountId
import io.reflectoring.buckpal.application.domain.model.Money
import jakarta.validation.constraints.Positive

/**
 * 口座間送金操作データクラス。
 *
 * @property sourceAccountId 送金元口座ID
 * @property targetAccountId 送金先口座ID
 * @property money 送金金額
 */
data class SendMoneyCommandDto(
    val sourceAccountId: AccountId,
    val targetAccountId: AccountId,
    // @field を付与することでJavaのフィールドレベルのバリデーションアノテーションをKotlinのプロパティに適用する
    // Kotlinのvalue classは @JvmInline によって内部プロパティを単一で保持するJavaクラスとしてコンパイル時に展開されるため、
    // バリデーションアノテーションはその内部プロパティに直接適用される。よってここではMoney型の内部プロパティであるBigInteger型をバリデートしている。
    @field:Positive(message = "金額は正の値で入力してください")
    val money: Money
)