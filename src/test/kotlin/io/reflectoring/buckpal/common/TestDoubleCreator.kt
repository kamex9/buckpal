package io.reflectoring.buckpal.common

import io.reflectoring.buckpal.common.properties.CommonProperties
import io.reflectoring.buckpal.common.util.MessageUtils
import org.springframework.context.support.StaticMessageSource
import java.util.*

object TestDoubleCreator {
    private fun dummyCommonProperties(): CommonProperties {
        return CommonProperties(
            "ja-JP",
            "Asia/Tokyo",
        )
    }

    fun fakeMessageUtils(userMessages: Map<String, String>?, systemMessages: Map<String, String>?): MessageUtils {
        val commonProperties = dummyCommonProperties()
        val userMessageSource = createStaticMessageSource(userMessages)
        val systemMessageSource = createStaticMessageSource(systemMessages)
        return MessageUtils(commonProperties, userMessageSource, systemMessageSource)
    }

    private fun createStaticMessageSource(messages: Map<String, String>?): StaticMessageSource {
        val messageSource = StaticMessageSource()
        messages?.forEach { (code, message) ->
            messageSource.addMessage(code, Locale.forLanguageTag("ja-JP"), message)
        }
        return messageSource
    }
}