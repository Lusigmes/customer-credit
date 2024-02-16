package com.example.customercredit.projections

import com.example.customercredit.model.entity.Credit
import com.example.customercredit.model.entity.Customer
import com.example.customercredit.model.enums.Status
import java.math.BigDecimal
import java.time.LocalDate

data class CreditProjection(
        var creditValue: BigDecimal,
        var dayFirstInstallment: LocalDate,
        var numberOfInstallment: Int,
        var status: Status,
        var emailCustomer: String?,
        var incomeCustomer: BigDecimal?
) {
    constructor(credit: Credit): this(
            creditValue = credit.creditValue,
            dayFirstInstallment = credit.dayFirstInstallment,
            numberOfInstallment = credit.numberOfInstallments,
            status = credit.status,
            emailCustomer = credit.customer?.email,
            incomeCustomer = credit.customer?.income
    )


}