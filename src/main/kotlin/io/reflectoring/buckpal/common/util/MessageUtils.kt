package io.reflectoring.buckpal.common.util

import io.reflectoring.buckpal.common.properties.CommonProperties
import org.springframework.context.MessageSource
import org.springframework.stereotype.Component
import java.util.Locale

/**
 * メッセージユーティリティクラス。
 * メッセージソースを使用してメッセージを取得します。
 *
 * @property commonProperties アプリケーション共通の設定
 * @property userMessageSource ユーザー向けメッセージソース
 * @property systemMessageSource システム運用者向けメッセージソース
 */
//@Component
class MessageUtils(
    private val commonProperties: CommonProperties,
    private val userMessageSource: MessageSource,
    private val systemMessageSource: MessageSource
) {

    /**
     * ユーザー向けメッセージを取得します。
     *
     * @param code メッセージコード
     * @param params メッセージ引数
     * @return メッセージ
     */
    fun getUserMsg(code: String, vararg params: Any): String {
        return userMessageSource.getMessage(code, params, Locale.forLanguageTag(commonProperties.locale))
    }

    /**
     * システム運用者向けメッセージを取得します。
     *
     * @param code メッセージコード
     * @param params メッセージ引数
     * @return メッセージ
     */
    fun getSystemMsg(code: String, vararg params: Any): String {
        return systemMessageSource.getMessage(code, params, Locale.forLanguageTag(commonProperties.locale))
    }
}
