package com.example.customercredit.service.impl

import com.example.customercredit.model.entity.Credit
import com.example.customercredit.repository.CreditRepository
import com.example.customercredit.service.ICreditService
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreditService(private val creditRepository: CreditRepository): ICreditService {



    override fun save(credit: Credit): Credit = this.creditRepository.save(credit)


    override fun findAllByCustomer(customerId: Long): List<Credit> {
        TODO("Not yet implemented")
    }

    override fun findByCreditCode(creditCode: UUID): Credit {
        TODO("Not yet implemented")
    }
}