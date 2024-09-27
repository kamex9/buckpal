package io.reflectoring.buckpal.application.port.`in`.sendmoney

import io.reflectoring.buckpal.application.domain.model.Account.AccountId

class TargetAccountNotFoundException(val accountId: AccountId, val context: SendMoneyCommandDto) :
    RuntimeException("accountId=${accountId}") {
}