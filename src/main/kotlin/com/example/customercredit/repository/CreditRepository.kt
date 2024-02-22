package com.example.customercredit.repository

import com.example.customercredit.model.entity.Credit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
@RepositoryRestResource(collectionResourceRel = "credits", path = "credits")
interface CreditRepository: JpaRepository<Credit, Long>{
    fun findByCreditCode(creditCode: UUID ): Credit? //função usando o spring data

    @Query("select * from credit where customer_id = ?1", nativeQuery = true)
    fun findAllByCustomerId(customerId: Long): List<Credit>

}