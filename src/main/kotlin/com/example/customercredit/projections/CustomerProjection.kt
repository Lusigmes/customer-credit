package com.example.customercredit.projections

import com.example.customercredit.model.entity.Adress
import com.example.customercredit.model.entity.Customer
import java.math.BigDecimal

data class CustomerProjection(
//usa projection para exibir dados selecionados do cliente
        var firstName: String,
        var lastName: String,
        var cpf: String,
        var income: BigDecimal,
        var email: String,
        var zipCode: String,
        var street: String
){
    //cria um construtor para receber um cliente e exibir alguns de seus dados
    constructor(customer: Customer): this(
            firstName = customer.firstName,
            lastName = customer.lastName,
            cpf = customer.cpf,
            income = customer.income,
            email = customer.email,
            zipCode = customer.adress.zipCode,
            street = customer.adress.street

    )
}