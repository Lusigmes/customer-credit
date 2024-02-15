package com.example.customercredit.service

import com.example.customercredit.model.entity.Customer
import org.springframework.stereotype.Service


interface ICustomerService {
    fun save(customer: Customer): Customer
    fun findById(id: Long): Customer
    fun delete(id: Long)
}