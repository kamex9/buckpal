package io.reflectoring.buckpal.application.port.`in`.sendmoney

import io.reflectoring.buckpal.application.domain.model.Money

/**
 * 送金額がしきい値を超えた場合にスローされる例外クラス。
 *
 * @param maxThresholdMoney 送金上限金額
 * @param actualMoney 送金しようとした金額
 */
class ThresholdExceededException(val maxThresholdMoney: Money, val actualMoney: Money) :
    RuntimeException("maxThresholdMoney=${maxThresholdMoney}, actualMoney=${actualMoney}")
