package com.example.customercredit.dto

import com.example.customercredit.model.entity.Credit
import com.example.customercredit.model.entity.Customer
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.LocalDate
import java.time.temporal.ChronoUnit


data class CreditDTO(
//cadastrar dados de um emprestimo
        @field:NotNull(message = "Invalid field credit value")  var creditValue: BigDecimal,
        @field:Future var dayFirstInstallment: LocalDate,
        @field:Max(value = 48, message = "Maximum installments exceeded (48)") var numberOfInstallment: Int,
        @field:NotNull(message = "Invalid field customer id") var customerId: Long
) {
    companion object{
        private const val MAX_INSTALLMENTS = 48
        private const val MAX_FIRST_INSTALLMENT_MONTHS = 3L
    }init {
        require(numberOfInstallment <= MAX_INSTALLMENTS) {
            "Maximum installments exceeded (48)"
        }
        require(LocalDate.now().until(dayFirstInstallment, ChronoUnit.MONTHS) <= MAX_FIRST_INSTALLMENT_MONTHS) {
            "First installment date must be within 3 months from today"
        }
    }
    fun toEntity(): Credit = Credit(
            creditValue = this.creditValue,
            dayFirstInstallment = this.dayFirstInstallment,
            numberOfInstallments = this.numberOfInstallment,
            customer = Customer(id = this.customerId)
    )
}