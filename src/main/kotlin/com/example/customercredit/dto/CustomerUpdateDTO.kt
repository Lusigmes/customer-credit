package com.example.customercredit.dto


import com.example.customercredit.model.entity.Customer
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class CustomerUpdateDTO(
// usa o dto para atualizar dados selecionados de um cliente
        @field:NotBlank(message = "Invalid field first name") var firstName: String,
        @field:NotBlank(message = "Invalid field last name") var lastName: String,
        @field:NotNull(message = "Invalid field income") var income: BigDecimal,
        @field:NotBlank(message = "Invalid field zip code") var zipCode: String,
        @field:NotBlank(message = "Invalid field street") var street: String
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