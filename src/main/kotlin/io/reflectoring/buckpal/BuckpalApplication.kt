package io.reflectoring.buckpal

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories

/**
 * メインアプリケーションクラス。
 * Spring Bootアプリケーションのエントリポイントです。
 */
@SpringBootApplication
@ConfigurationPropertiesScan
@EnableJdbcRepositories
class BuckpalApplication

/**
 * アプリケーションのエントリポイント。
 * Spring Bootアプリケーションを起動します。
 *
 * @param args コマンドライン引数
 */
fun main(args: Array<String>) {
    runApplication<BuckpalApplication>(*args)
}

