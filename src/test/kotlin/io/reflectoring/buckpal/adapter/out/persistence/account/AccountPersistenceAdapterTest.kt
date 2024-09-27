package io.reflectoring.buckpal.adapter.out.persistence.account

import io.reflectoring.buckpal.application.domain.model.Account.AccountId
import io.reflectoring.buckpal.application.domain.model.ActivityHistory
import io.reflectoring.buckpal.application.domain.model.Money
import io.reflectoring.buckpal.common.AccountBuilder
import io.reflectoring.buckpal.common.ActivityBuilder
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.context.annotation.Import
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import java.time.LocalDate
import kotlin.test.Test

@ExtendWith(SpringExtension::class)
@DataJdbcTest
@Import(AccountPersistenceAdapter::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountPersistenceAdapterTest {

    @Autowired
    private lateinit var adapterUnderTest: AccountPersistenceAdapter

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @Autowired
    private lateinit var activityRepository: ActivityRepository

    companion object {
        @JvmStatic
        @DynamicPropertySource
        fun dynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") { "jdbc:h2:mem:testdb;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH" }
            registry.add("spring.datasource.username") { "sa" }
            registry.add("spring.datasource.password") { "" }
            registry.add("spring.datasource.driver-class-name") { "org.h2.Driver" }
        }
    }

    @AfterEach
    fun tearDown() {
        jdbcTemplate.execute("TRUNCATE TABLE account;")
        jdbcTemplate.execute("TRUNCATE TABLE activity;")
    }

    @Test
    fun `test_loadAccount 基準日以降の取引が口座に設定され、かつ基準日時点残高と現在残高が正しく算出されていればOK`() {
        createDefaultRecords()
        val account = adapterUnderTest.loadAccount(AccountId(1L), LocalDate.of(2024, 7, 2))

        assertThat(account).isNotNull()
        assertThat(account?.activityHistory?.getActivities()).hasSize(2)
        assertThat(account?.baselineBalance).isEqualTo(Money.of(500L))
        assertThat(account?.getCurrentBalance()).isEqualTo(Money.of(1000L))
    }

    @Test
    fun `test_updateActivities 取引がDB登録されればOK`() {
        val account = AccountBuilder()
            .withBaselineBalance(Money.of(555L))
            .withActivityHistory(
                ActivityHistory.of(
                    ActivityBuilder()
                        .withId(null)
                        .withMoney(Money.of(120L))
                        .build()
                )
            )
            .build()

        adapterUnderTest.updateActivities(account)

        assertThat(activityRepository.count()).isEqualTo(1)
        assertThat(activityRepository.findById(1L).get().amount).isEqualTo(120L)
    }

    private fun createDefaultRecords() {
        // ------------------------------------------------------
        // account
        // ------------------------------------------------------
        jdbcTemplate.execute(
            """
            INSERT INTO account (id, owner_last_name, owner_first_name, bank_code, branch_number, account_number, created_at, updated_at)
            VALUES (1, 'Doe', 'John', 123, 456, 7890123, '2024-01-01 10:00:00', '2024-01-01 10:00:00');
        """
        )

        jdbcTemplate.execute(
            """
            INSERT INTO account (id, owner_last_name, owner_first_name, bank_code, branch_number, account_number, created_at, updated_at)
            VALUES (2, 'Smith', 'Jane', 321, 654, 3210987, '2024-01-01 11:00:00', '2024-01-01 11:00:00');
        """
        )

        // ------------------------------------------------------
        // activity
        // ------------------------------------------------------
        jdbcTemplate.execute(
            """
            INSERT INTO activity (id, created_at, owner_account_id, source_account_id, target_account_id, amount)
            VALUES (91, '2024-07-01 08:00:00.0', 1, 1, 2, 500);
        """
        )

        jdbcTemplate.execute(
            """
            INSERT INTO activity (id, created_at, owner_account_id, source_account_id, target_account_id, amount)
            VALUES (92, '2024-07-01 08:00:00.0', 2, 1, 2, 500);
        """
        )

        jdbcTemplate.execute(
            """
            INSERT INTO activity (id, created_at, owner_account_id, source_account_id, target_account_id, amount)
            VALUES (93, '2024-07-01 10:00:00.0', 1, 2, 1, 1000);
        """
        )

        jdbcTemplate.execute(
            """
            INSERT INTO activity (id, created_at, owner_account_id, source_account_id, target_account_id, amount)
            VALUES (94, '2024-07-01 10:00:00.0', 2, 2, 1, 1000);
        """
        )

        jdbcTemplate.execute(
            """
            INSERT INTO activity (id, created_at, owner_account_id, source_account_id, target_account_id, amount)
            VALUES (95, '2024-07-02 09:00:00.0', 1, 1, 2, 1500);
        """
        )

        jdbcTemplate.execute(
            """
            INSERT INTO activity (id, created_at, owner_account_id, source_account_id, target_account_id, amount)
            VALUES (96, '2024-07-02 09:00:00.0', 2, 1, 2, 1500);
        """
        )

        jdbcTemplate.execute(
            """
            INSERT INTO activity (id, created_at, owner_account_id, source_account_id, target_account_id, amount)
            VALUES (97, '2024-07-03 10:00:00.0', 1, 2, 1, 2000);
        """
        )

        jdbcTemplate.execute(
            """
            INSERT INTO activity (id, created_at, owner_account_id, source_account_id, target_account_id, amount)
            VALUES (98, '2024-07-03 10:00:00.0', 2, 2, 1, 2000);
        """
        )
    }
}
