package io.reflectoring.buckpal

import io.reflectoring.buckpal.application.service.CreateFakeAccountService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import kotlin.system.exitProcess

@Profile("batch-create-fake-account")
@Component
class CreateFakeAccountBatch(
    private val service: CreateFakeAccountService
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        try {
            service.createFakeAccount(10)
            exitProcess(0)
        } catch (e: Exception) {
            println("An error occurred: ${e.message}")
            e.printStackTrace()
            exitProcess(1)
        }
    }
}