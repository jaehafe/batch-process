package org.jh.batchprocess.core.job.purchageconfirmed

import jakarta.transaction.TransactionManager
import org.jh.batchprocess.domain.entity.order.OrderItem
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
@EnableBatchProcessing
class PurchaseConfirmedJobConfig(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    @Qualifier("deliveryCompletedJpaItemReader") private val deliveryCompletedJpaItemReader: JpaPagingItemReader<OrderItem>,
) {

    val JOB_NAME = "purchaseConfirmedJob"
    val chunkSize = 500

    @Bean
    fun purchaseConfirmedJob(): Job {
        return JobBuilder(JOB_NAME, jobRepository)
            .start(purchaseConfirmedStep())
            .build()
    }

    @Bean
    @JobScope
    fun purchaseConfirmedStep(): Step {
        return StepBuilder(JOB_NAME+"_step", jobRepository)
            .chunk<OrderItem, OrderItem>(this.chunkSize, transactionManager)
            .reader(deliveryCompletedJpaItemReader)
//            .processor()
//            .writer()
            .build()

    }
}