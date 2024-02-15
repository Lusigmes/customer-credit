package com.example.customercredit.service

import com.example.customercredit.model.entity.Credit
import org.springframework.stereotype.Service
import java.util.UUID


interface ICreditService {
    fun save(credit: Credit): Credit
    //pega o id o cliente e lista todas solicitações de cretito feitas pelo cliente
    fun findAllByCustomer(customerId: Long): List<Credit>
    fun findByCreditCode(creditCode: UUID): Credit
}