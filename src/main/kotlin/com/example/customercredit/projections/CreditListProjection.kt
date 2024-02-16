package com.example.customercredit.projections

import com.example.customercredit.model.entity.Credit
import java.math.BigDecimal
import java.util.UUID

data class CreditListProjection(
//mostrar alguns dados do emprestimo
        var creditCode: UUID,
        var creditValue: BigDecimal,
        var numberOfInstallment: Int
) {
    //recebe o credito de parametro e exibe alguns dados
    constructor(credit: Credit): this(
            creditCode = credit.creditCode,
            creditValue = credit.creditValue,
            numberOfInstallment = credit.numberOfInstallments
    )
}