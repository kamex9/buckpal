package io.reflectoring.buckpal.adapter.out.persistence.account

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.ListCrudRepository
import java.time.LocalDate

/**
 * 取引データの永続化リポジトリ。
 */
interface ActivityRepository : ListCrudRepository<ActivityEntity, Long> {

    /**
     * 対象口座の基準日以降の取引データを取得します。
     *
     * @param ownerAccountId 口座契約者の口座ID
     * @param baselineDate 基準日
     * @return 取引データの永続化エンティティのリスト
     */
    @Query("""
        SELECT id, owner_account_id, source_account_id, target_account_id, created_at, amount
        FROM activity 
        WHERE owner_account_id = :ownerAccountId
          AND created_at >= :baselineDate
    """)
    fun findByOwnerSince(ownerAccountId: Long, baselineDate: LocalDate) : List<ActivityEntity>

    /**
     * 対象口座の基準日以前の取引データから入金合計金額を算出します。
     *
     * @param ownerAccountId 口座契約者の口座ID
     * @param baselineDate 基準日
     * @return 入金合計金額
     */
    @Query("""
        SELECT COALESCE(SUM(amount), 0)
        FROM activity 
        WHERE target_account_id = :ownerAccountId
          AND owner_account_id = :ownerAccountId
          AND created_at < :baselineDate
    """)
    fun getTotalDepositsUntil(ownerAccountId: Long, baselineDate: LocalDate) : Long

    /**
     * 対象口座の基準日以前の取引データから出金合計金額を算出します。
     *
     * @param ownerAccountId 口座契約者の口座ID
     * @param baselineDate 基準日
     * @return 出金合計金額
     */
    @Query("""
        SELECT COALESCE(SUM(amount), 0)
        FROM activity 
        WHERE source_account_id = :ownerAccountId
          AND owner_account_id = :ownerAccountId
          AND created_at < :baselineDate
    """)
    fun getTotalWithdrawalsUntil(ownerAccountId: Long, baselineDate: LocalDate) : Long

}