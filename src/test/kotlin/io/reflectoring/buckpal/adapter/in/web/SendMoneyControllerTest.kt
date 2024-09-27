package io.reflectoring.buckpal.adapter.`in`.web

import org.mockito.Mockito.`when`
import com.fasterxml.jackson.databind.ObjectMapper
import io.reflectoring.buckpal.adapter.`in`.web.common.GlobalExceptionHandlerV2
import io.reflectoring.buckpal.application.domain.model.Account.AccountId
import io.reflectoring.buckpal.application.domain.model.Money
import io.reflectoring.buckpal.application.port.`in`.sendmoney.SendMoneyCommandDto
import io.reflectoring.buckpal.application.port.`in`.sendmoney.SendMoneyUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.web.bind.MethodArgumentNotValidException
import org.assertj.core.api.Assertions.*
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.test.web.servlet.ResultActions
import kotlin.test.Test

//@WebMvcTest(controllers = [SendMoneyController::class])
@SpringBootTest
@AutoConfigureMockMvc
class SendMoneyControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var sendMoneyUseCase: SendMoneyUseCase

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private fun mockMvcPerform(dtoStr: String): ResultActions {
        return mockMvc.perform(
            post("/accounts/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoStr)
        ).andDo(print())
    }

    @Test
    fun `test_sendMoney 口座IDがNullでなく金額が正の数であれば200応答`() {
        val dto = SendMoneyCommandDto(
            sourceAccountId = AccountId(42L),
            targetAccountId = AccountId(43L),
            money = Money.of(100L)
        )
        `when`(sendMoneyUseCase.sendMoney(dto)).thenReturn(true)

        mockMvcPerform(objectMapper.writeValueAsString(dto))
            .andExpect { result ->
                assertThat(result.response.status).isEqualTo(HttpStatus.OK.value())
                assertThat(result.response.contentAsString).matches("""^\{"success":true}$""")
            }
    }

//    @Test
//    fun `test_sendMoney 口座IDがNullでなく金額が負の数であればMethodArgumentNotValidExceptionで400応答`() {
//        val dto = SendMoneyCommandDto(
//            sourceAccountId = AccountId(42L),
//            targetAccountId = AccountId(43L),
//            money = Money.of(-200L)
//        )
//
//        mockMvcPerform(objectMapper.writeValueAsString(dto))
//            .andExpect(status().isBadRequest)
//            .andExpect { result ->
//                assertThat(result.resolvedException).isInstanceOf(MethodArgumentNotValidException::class.java)
//            }
//            .andExpect(content().json("""
//            {
//              "type": "about:blank",
//              "title": "Bad Request",
//              "status": 400,
//              "detail": "送金元口座が存在しません",
//              "instance": "/accounts/send"
//            }
//        """.trimIndent()))
//    }
//
//    @Test
//    fun `test_sendMoney 口座IDがNullでなく金額が0であればMethodArgumentNotValidExceptionで400応答`() {
//        val dto = SendMoneyCommandDto(
//            sourceAccountId = AccountId(42L),
//            targetAccountId = AccountId(43L),
//            money = Money.of(0L)
//        )
//
//        mockMvcPerform(objectMapper.writeValueAsString(dto))
//            .andExpect { result ->
//                assertThat(result.response.status).isEqualTo(HttpStatus.BAD_REQUEST.value())
//                assertThat(result.resolvedException).isInstanceOf(MethodArgumentNotValidException::class.java)
//                assertThat(result.response.contentAsString).matches("""^\{"error":.*}$""")
//            }
//    }
//
//    @Test
//    fun `test_sendMoney 口座IDがNullで金額が正の数であればHttpMessageNotReadableExceptionで400応答`() {
//        val dtoStr = """
//            {"sourceAccountId":null,"targetAccountId":43,"money":100}
//        """.trimIndent()
//
//        mockMvcPerform(dtoStr)
//            .andExpect { result ->
//                assertThat(result.response.status).isEqualTo(HttpStatus.BAD_REQUEST.value())
//                assertThat(result.resolvedException).isInstanceOf(HttpMessageNotReadableException::class.java)
//                assertThat(result.response.contentAsString).matches("""^\{"error":.*}$""")
//            }
//    }
//
//    @Test
//    fun `test_sendMoney 口座IDがNullで金額が負の数であればHttpMessageNotReadableExceptionで400応答`() {
//        val dtoStr = """
//            {"sourceAccountId":null,"targetAccountId":43,"money":-200}
//        """.trimIndent()
//
//        mockMvcPerform(dtoStr)
//            .andExpect { result ->
//                assertThat(result.response.status).isEqualTo(HttpStatus.BAD_REQUEST.value())
//                assertThat(result.resolvedException).isInstanceOf(HttpMessageNotReadableException::class.java)
//                assertThat(result.response.contentAsString).matches("""^\{"error":.*}$""")
//            }
//    }
}