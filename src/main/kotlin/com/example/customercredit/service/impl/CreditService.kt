package com.example.customercredit.service.impl

import com.example.customercredit.model.entity.Credit
import com.example.customercredit.repository.CreditRepository
import com.example.customercredit.service.ICreditService
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreditService(private val creditRepository: CreditRepository,
                    private val customerService: CustomerService ): ICreditService {

    override fun save(credit: Credit): Credit {
        credit.apply {
            customer = customerService.findById(credit.customer?.id!!)
        }//pega o atributo cliente dentro da classe credito,
        // no customerService chama o findById e verifica se o id não é nulo
        return this.creditRepository.save(credit)
    }

    override fun findAllByCustomer(customerId: Long): List<Credit> =
            this.creditRepository.findAllByCustomerId(customerId)

    override fun findByCreditCode(customerId: Long, creditCode: UUID): Credit {
        val credit = (this.creditRepository.findByCreditCode(creditCode) ?: throw Exception("Credit code $creditCode not found"))
        return if (credit.customer?.id == customerId) credit else
            throw Exception("Contact Admin")
    }
}