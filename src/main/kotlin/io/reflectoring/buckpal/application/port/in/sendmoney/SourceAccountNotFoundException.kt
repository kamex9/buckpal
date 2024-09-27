package io.reflectoring.buckpal.application.port.`in`.sendmoney

import io.reflectoring.buckpal.application.domain.model.Account.AccountId

class SourceAccountNotFoundException(val accountId: AccountId, val context: SendMoneyCommandDto) :
    RuntimeException("accountId=${accountId}") {
}