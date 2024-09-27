package io.reflectoring.buckpal.adapter.out.persistence.account

import org.springframework.data.repository.ListCrudRepository

/**
 * 口座データの永続化リポジトリ。
 */
interface AccountRepository: ListCrudRepository<AccountEntity, Long> {
}