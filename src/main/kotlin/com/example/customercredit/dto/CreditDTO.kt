package com.example.customercredit.dto

import com.example.customercredit.model.entity.Credit
import com.example.customercredit.model.entity.Customer
import com.example.customercredit.model.enums.Status
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class CreditDTO(
//cadastrar dados de um emprestimo
        var creditValue: BigDecimal,
        var dayFirstInstallment: LocalDate,
        var numberOfInstallment: Int,
        var customerId: Long
) {
    fun toEntity(): Credit = Credit(
            creditValue = this.creditValue,
            dayFirstInstallment = this.dayFirstInstallment,
            numberOfInstallments = this.numberOfInstallment,
            customer = Customer(id = this.customerId)
    )
}