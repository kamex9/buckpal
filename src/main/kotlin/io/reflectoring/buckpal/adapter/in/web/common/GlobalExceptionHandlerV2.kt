package io.reflectoring.buckpal.adapter.`in`.web.common

import io.reflectoring.buckpal.application.port.`in`.sendmoney.SourceAccountNotFoundException
import io.reflectoring.buckpal.application.port.`in`.sendmoney.TargetAccountNotFoundException
import io.reflectoring.buckpal.application.port.`in`.sendmoney.ThresholdExceededException
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

/**
 * グローバルな例外ハンドラーで、コントローラーで発生する例外を処理します。
 * 各種例外に対して適切なエラーレスポンスを返します。
 *
 * @property messageSource メッセージソース
 */
@RestControllerAdvice
class GlobalExceptionHandlerV2(private val messageSource: MessageSource) : ResponseEntityExceptionHandler() {

    /**
     * `ThresholdExceededException` が発生した際に呼び出される例外ハンドラーです。
     * エラーメッセージとともに、閾値と実際の値をプロパティとして設定します。
     *
     * @param ex 発生した `ThresholdExceededException`
     * @return エラーレスポンスを含む `ProblemDetail`
     */
    @ExceptionHandler(ThresholdExceededException::class)
    fun handleThresholdExceededException(ex: ThresholdExceededException): ProblemDetail {
        val problemDetails = ProblemDetail
            .forStatusAndDetail(HttpStatus.BAD_REQUEST, getMessage("error.send-money.threshold.exceeded"))

        problemDetails.setProperty("maxThreshold", ex.maxThresholdMoney.amount)
        problemDetails.setProperty("actual", ex.actualMoney.amount)
        return problemDetails
    }

    /**
     * `SourceAccountNotFoundException` が発生した際に呼び出される例外ハンドラーです。
     * エラーメッセージを設定し、ソースアカウントが見つからないことを示します。
     *
     * @param ex 発生した `SourceAccountNotFoundException`
     * @return エラーレスポンスを含む `ProblemDetail`
     */
    @ExceptionHandler(SourceAccountNotFoundException::class)
    fun handleSourceAccountNotFoundException(ex: SourceAccountNotFoundException): ProblemDetail {
        return ProblemDetail
            .forStatusAndDetail(HttpStatus.BAD_REQUEST, getMessage("error.send-money.source.account.not-found"))
    }

    /**
     * `TargetAccountNotFoundException` が発生した際に呼び出される例外ハンドラーです。
     * エラーメッセージを設定し、ターゲットアカウントが見つからないことを示します。
     *
     * @param ex 発生した `TargetAccountNotFoundException`
     * @return エラーレスポンスを含む `ProblemDetail`
     */
    @ExceptionHandler(TargetAccountNotFoundException::class)
    fun handleTargetAccountNotFoundException(ex: TargetAccountNotFoundException): ProblemDetail {
        return ProblemDetail
            .forStatusAndDetail(HttpStatus.BAD_REQUEST, getMessage("error.send-money.target.account.not-found"))
    }

    /**
     * メッセージソースからエラーメッセージを取得します。
     * 引数なしで呼び出す場合、空の引数配列を渡します。
     *
     * @param code メッセージコード
     * @return メッセージソースから取得したメッセージ
     */
    private fun getMessage(code: String): String {
        return getMessage(code, *arrayOfNulls<Any>(0))
    }

    /**
     * メッセージソースからエラーメッセージを取得します。
     *
     * @param code メッセージコード
     * @param args メッセージフォーマットに埋め込む引数
     * @return メッセージソースから取得したメッセージ
     */
    private fun getMessage(code: String, vararg args: Any?): String {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale())
    }
}
