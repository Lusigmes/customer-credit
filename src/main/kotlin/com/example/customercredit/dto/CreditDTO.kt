package com.example.customercredit.dto

import com.example.customercredit.model.entity.Credit
import com.example.customercredit.model.entity.Customer
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.LocalDate


data class CreditDTO(
//cadastrar dados de um emprestimo
        @field:NotNull(message = "Invalid field credit value")  var creditValue: BigDecimal,
        @field:Future var dayFirstInstallment: LocalDate,
        @field:Max(value = 5, message = "limit exceeded (5)") var numberOfInstallment: Int,
        @field:NotNull(message = "Invalid field customer id") var customerId: Long
) {
    fun toEntity(): Credit = Credit(
            creditValue = this.creditValue,
            dayFirstInstallment = this.dayFirstInstallment,
            numberOfInstallments = this.numberOfInstallment,
            customer = Customer(id = this.customerId)
    )
}