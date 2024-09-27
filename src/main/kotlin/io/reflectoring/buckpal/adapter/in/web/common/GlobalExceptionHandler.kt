package io.reflectoring.buckpal.adapter.`in`.web.common

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

/**
 * グローバルな例外ハンドラーで、コントローラーで発生する例外を処理します。
 * 各種例外に対して適切なエラーレスポンスを返します。
 */
//@RestControllerAdvice
class GlobalExceptionHandler {

    /**
     * リクエストボディが正しく読み取れない場合の例外を処理します。
     * @param ex `Exception` 例外インスタンス
     * @return エラーレスポンスを含む `ResponseEntity`
     */
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleSuperExceptions(ex: Exception): ResponseEntity<ErrorResponse> {
        val errorContent = ErrorContent(
            timestamp = LocalDateTime.now(),
            code = "E_9001",
            reason = "exception occurred",
            details = ex.message
        )
        return ResponseEntity(ErrorResponse(errorContent), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    /**
     * Bean Validation の例外を処理します。
     *
     * @param ex `MethodArgumentNotValidException` 例外インスタンス
     * @return エラーレスポンスを含む `ResponseEntity`
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBeanValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errors = ex.bindingResult.allErrors.map { error ->
            val fieldError = error as FieldError
            FieldErrorDetail(
                field = fieldError.field,
                value = fieldError.rejectedValue,
                message = fieldError.defaultMessage ?: "Invalid value"
            )
        }
        val errorContent = ErrorContent(
            timestamp = LocalDateTime.now(),
            code = "E_1001",
            reason = "invalid request body",
            details = errors
        )
        return ResponseEntity(ErrorResponse(errorContent), HttpStatus.BAD_REQUEST)
    }

    /**
     * リクエストボディが正しく読み取れない場合の例外を処理します。
     * @param ex `HttpMessageNotReadableException` 例外インスタンス
     * @return エラーレスポンスを含む `ResponseEntity`
     */
    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleInvalidNullRequestExceptions(ex: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        val errorContent = ErrorContent(
            timestamp = LocalDateTime.now(),
            code = "E_1002",
            reason = "invalid request body",
            details = ex.message
        )
        return ResponseEntity(ErrorResponse(errorContent), HttpStatus.BAD_REQUEST)
    }

    /**
     * エラーレスポンスのデータクラスです。
     * @param error エラーコンテンツ
     */
    data class ErrorResponse(
        val error: ErrorContent
    )

    /**
     * エラーコンテンツのデータクラスです。
     * @param timestamp エラーが発生した日時
     * @param code エラーコード
     * @param reason エラーの理由
     * @param details エラーの詳細
     */
    data class ErrorContent(
        val timestamp: LocalDateTime,
        val code: String,
        val reason: String,
        val details: Any?
    )

    /**
     * フィールドエラーの詳細を示すデータクラスです。
     * @param field フィールド名
     * @param value フィールドの値
     * @param message エラーメッセージ
     */
    data class FieldErrorDetail(
        val field: String,
        val value: Any?,
        val message: String
    )
}
