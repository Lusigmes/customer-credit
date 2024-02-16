package com.example.customercredit.dto

import com.example.customercredit.model.entity.Adress
import com.example.customercredit.model.entity.Customer
import java.math.BigDecimal

data class CustomerUpdateDTO(
// usa o dto para atualizar dados selecionados de um cliente
        var firstName: String,
        var lastName: String,
        var income: BigDecimal,
        var zipCode: String,
        var street: String
) {
    //recebe um clieinte como parametro para atualizar alguns de seus dados
    fun toEntity(customer: Customer): Customer {
        customer.firstName = this.firstName
        customer.lastName = this.lastName
        customer.income = this.income
        customer.adress.zipCode = this.zipCode
        customer.adress.street = this.street

        return customer
    }
}