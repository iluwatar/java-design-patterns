/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

// ABOUTME: Spring Boot configuration class with annotation-based JPA setup.
// ABOUTME: Configures DataSource, EntityManagerFactory, and TransactionManager for H2 database.
package com.iluwatar.repository

import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.commons.dbcp.BasicDataSource
import org.hibernate.jpa.HibernatePersistenceProvider
import org.springframework.boot.SpringBootConfiguration
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import java.util.Properties
import javax.sql.DataSource

private val logger = KotlinLogging.logger {}

/**
 * This is the same example as in [App] but with annotations based configuration for Spring.
 */
@EnableJpaRepositories
@SpringBootConfiguration
open class AppConfig {

    /**
     * Creation of H2 db.
     *
     * @return A new Instance of DataSource
     */
    @Bean(destroyMethod = "close")
    open fun dataSource(): DataSource {
        return BasicDataSource().apply {
            driverClassName = "org.h2.Driver"
            url = "jdbc:h2:mem:databases-person"
            username = "sa"
            password = "sa"
        }
    }

    /**
     * Factory to create a specific instance of Entity Manager.
     */
    @Bean
    open fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        return LocalContainerEntityManagerFactoryBean().apply {
            setDataSource(dataSource())
            setPackagesToScan("com.iluwatar")
            setPersistenceProvider(HibernatePersistenceProvider())
            setJpaProperties(jpaProperties())
        }
    }

    /**
     * Get transaction manager.
     */
    @Bean
    open fun transactionManager(): JpaTransactionManager {
        return JpaTransactionManager().apply {
            entityManagerFactory = entityManagerFactory().`object`
        }
    }

    companion object {
        /**
         * Properties for Jpa.
         */
        private fun jpaProperties(): Properties {
            return Properties().apply {
                setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
                setProperty("hibernate.hbm2ddl.auto", "create-drop")
            }
        }

        /**
         * Program entry point.
         *
         * @param args command line args
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val context = AnnotationConfigApplicationContext(AppConfig::class.java)
            val repository = context.getBean(PersonRepository::class.java)

            val peter = Person("Peter", "Sagan", 17)
            val nasta = Person("Nasta", "Kuzminova", 25)
            val john = Person("John", "lawrence", 35)
            val terry = Person("Terry", "Law", 36)

            // Add new Person records
            repository.save(peter)
            repository.save(nasta)
            repository.save(john)
            repository.save(terry)

            // Count Person records
            logger.info { "Count Person records: ${repository.count()}" }

            // Print all records
            val persons = repository.findAll()
            persons.forEach { logger.info { it.toString() } }

            // Update Person
            nasta.name = "Barbora"
            nasta.surname = "Spotakova"
            repository.save(nasta)

            repository.findById(2L).ifPresent { p -> logger.info { "Find by id 2: $p" } }

            // Remove record from Person
            repository.deleteById(2L)

            // count records
            logger.info { "Count Person records: ${repository.count()}" }

            // find by name
            repository
                .findOne(PersonSpecifications.NameEqualSpec("John"))
                .ifPresent { p -> logger.info { "Find by John is $p" } }

            // find by age
            val personsByAge = repository.findAll(PersonSpecifications.AgeBetweenSpec(20, 40))

            logger.info { "Find Person with age between 20,40: " }
            personsByAge.forEach { logger.info { it.toString() } }

            context.close()
        }
    }
}
