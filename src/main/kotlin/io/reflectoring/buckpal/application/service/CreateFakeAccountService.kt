package io.reflectoring.buckpal.application.service

import com.github.javafaker.Faker
import io.reflectoring.buckpal.adapter.out.persistence.account.AccountEntity
import io.reflectoring.buckpal.adapter.out.persistence.account.AccountRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.Locale

@Service
@Transactional
class CreateFakeAccountService(
    private val accountRepository: AccountRepository
) {
    fun createFakeAccount(num: Int) {
        val faker = Faker(Locale("ja"))

        if (num < 1) return

        for (i in 1..num) {
            var account = AccountEntity(
                id = 0L,
                ownerLastName = faker.name().lastName(),
                ownerFirstName = faker.name().firstName(),
                bankCode = faker.number().numberBetween(1000, 9999), // 4桁の銀行コード
                branchNumber = faker.number().numberBetween(100, 999), // 3桁の支店番号
                accountNumber = faker.number().numberBetween(1000000, 9999999), // 7桁の口座番号
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
            account = accountRepository.save(account)
            println("No.${i}: $account")
        }

    }
}