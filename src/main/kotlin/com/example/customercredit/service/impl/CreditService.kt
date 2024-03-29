package com.example.customercredit.service.impl

import com.example.customercredit.exceptions.BusinessException
import com.example.customercredit.model.entity.Credit
import com.example.customercredit.repository.CreditRepository
import com.example.customercredit.service.ICreditService
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class CreditService(private val creditRepository: CreditRepository,
                    private val customerService: CustomerService ): ICreditService {

    override fun save(credit: Credit): Credit {
        this.validDayFirstInstallment(credit.dayFirstInstallment)
        credit.apply {
            customer = customerService.findById(credit.customer?.id!!)
//            customer = customerService.findById(credit.customer?.id ?: throw IllegalArgumentException("Customer ID cannot be null"))

        }
        //pega o atributo cliente dentro da classe credito,
        // no customerService chama o findById e verifica se o id não é nulo
        return this.creditRepository.save(credit)
    }



    override fun findAllByCustomer(customerId: Long): List<Credit> =
            this.creditRepository.findAllByCustomerId(customerId)

    override fun findByCreditCode(customerId: Long, creditCode: UUID): Credit {
        val credit = (this.creditRepository.findByCreditCode(creditCode) ?: throw Exception("Credit code $creditCode not found"))
        return if (credit.customer?.id == customerId) credit else
            throw IllegalArgumentException("Contact Admin")
    }
    private fun validDayFirstInstallment(dayFirstInstallment: LocalDate): Boolean {
        return if (dayFirstInstallment.isBefore(LocalDate.now().plusMonths(3))) true
        else throw BusinessException("Invalid Date")
    }
}