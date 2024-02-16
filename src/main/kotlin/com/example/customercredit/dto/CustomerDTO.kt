package com.example.customercredit.dto

import com.example.customercredit.model.entity.Adress
import com.example.customercredit.model.entity.Customer
import java.math.BigDecimal

data class CustomerDTO(
// usa o dto para cadastrar dados de um cliente
        var firstName: String,
        var lastName: String,
        var cpf: String,
        var income: BigDecimal,
        var email: String,
        var password: String,
        var zipCode: String,
        var street: String
){
    //cria um novo cliente
    fun toEntity(): Customer = Customer(
            firstName = this.firstName,
            lastName = this.lastName,
            cpf = this.cpf,
            income = this.income,
            email = this.email,
            password = this.password,
            adress = Adress(this.street, this.zipCode)
    )
}