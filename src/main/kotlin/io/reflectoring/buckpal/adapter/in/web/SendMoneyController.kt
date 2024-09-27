package io.reflectoring.buckpal.adapter.`in`.web

import io.reflectoring.buckpal.application.port.`in`.sendmoney.SendMoneyCommandDto
import io.reflectoring.buckpal.application.port.`in`.sendmoney.SendMoneyUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

/**
 * 送金処理コントローラークラス。
 *
 * @property sendMoneyUseCase 送金処理ユースケースインターフェース
 */
@RestController
class SendMoneyController(
    private val sendMoneyUseCase: SendMoneyUseCase
) {

    /**
     * 送金リクエストを処理するメソッド。
     *
     * @param dto 送金リクエストデータ
     * @return 処理結果
     */
    @PostMapping(path = ["/accounts/send"])
    fun sendMoney(@Valid @RequestBody dto: SendMoneyCommandDto): ResponseEntity<Map<String, Boolean>> {
        val result = sendMoneyUseCase.sendMoney(dto)
        return ResponseEntity(mapOf("success" to result), HttpStatus.OK)
    }
}
