package io.reflectoring.buckpal.application.port.`in`.sendmoney

/**
 * 口座間送金操作ユースケース。
 */
interface SendMoneyUseCase {

    /**
     * 口座間送金操作を実行します。
     *
     * @param dto 口座間送金操作データ
     * @return 実行結果
     */
    fun sendMoney(dto: SendMoneyCommandDto): Boolean
}