package io.reflectoring.buckpal.common.properties

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * 共通設定プロパティ値保持クラス。
 *
 * @property locale デフォルトのロケール
 * @property timezone デフォルトのタイムゾーン
 */
@ConfigurationProperties(prefix = "common")
data class CommonProperties(
    val locale: String?,
    val timezone: String?,
)