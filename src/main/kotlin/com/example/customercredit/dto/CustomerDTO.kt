package com.example.customercredit.dto

import com.example.customercredit.model.entity.Adress
import com.example.customercredit.model.entity.Customer
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.br.CPF
import java.math.BigDecimal

data class CustomerDTO(
// usa o dto para cadastrar dados de um cliente
       @field:NotBlank(message = "Invalid field first name") var firstName: String,
       @field:NotBlank(message = "Invalid field last name") var lastName: String,
       @field:NotBlank(message = "Invalid field cpf") @field:CPF(message = "Invalid CPF") var cpf: String,
       @field:NotNull(message = "Invalid field income") var income: BigDecimal,
       @field:NotBlank(message = "Invalid field email") @field:Email(message = "Invalid email") var email: String,
       @field:NotBlank(message = "Invalid field psw")var password: String,
       @field:NotBlank(message = "Invalid field zip code")var zipCode: String,
       @field:NotBlank(message = "Invalid field street") var street: String
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