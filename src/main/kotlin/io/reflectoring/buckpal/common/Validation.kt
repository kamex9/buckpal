package io.reflectoring.buckpal.common

import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validator
import jakarta.validation.Validation

/**
 * バリデーションユーティリティクラス。
 *
 * オブジェクトに対してバリデーションを実行するための静的メソッドを提供します。
 */
object Validation {

    /** シングルトンバリデーター */
//    private val validator: Validator = buildDefaultValidatorFactory().validator
    private val validator: Validator = Validation.byDefaultProvider().configure().buildValidatorFactory().validator

    /**
     * 指定されたオブジェクトのBeanバリデーションアノテーションを評価します。
     *
     * @param subject バリデーション対象のオブジェクト
     * @throws ConstraintViolationException バリデーションに失敗した場合にスローされます
     */
    fun <T> validate(subject: T) {
        val violations = validator.validate(subject)
        println("violations = $violations")
        if (violations.isNotEmpty()) {
            throw ConstraintViolationException(violations)
        }
    }
}