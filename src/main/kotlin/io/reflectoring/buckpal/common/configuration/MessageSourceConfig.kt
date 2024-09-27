package io.reflectoring.buckpal.common.configuration

import io.reflectoring.buckpal.common.properties.CommonProperties
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import java.util.Locale

/**
 * メッセージソースの設定クラス。
 * メッセージの国際化をサポートします。
 *
 * @property commonProperties アプリケーション共通の設定
 */
@Configuration
class MessageSourceConfig(private val commonProperties: CommonProperties) {

    /**
     * ユーザー向けメッセージソースのBeanを定義します。
     *
     * @return メッセージソース
     */
    @Bean
    fun userMessageSource(): MessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasename("classpath:messages_user_ja")
        messageSource.setDefaultLocale(Locale.forLanguageTag(commonProperties.locale))
        return messageSource
    }

    /**
     * システム運用者向けメッセージソースのBeanを定義します。
     *
     * @return メッセージソース
     */
    @Bean
    fun systemMessageSource(): MessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasename("classpath:messages_system_ja")
        messageSource.setDefaultEncoding("UTF-8")
        messageSource.setDefaultLocale(Locale.forLanguageTag(commonProperties.locale))
        return messageSource
    }
}
