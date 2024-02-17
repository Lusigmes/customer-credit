package com.example.customercredit.service

import com.example.customercredit.model.entity.Customer


interface ICustomerService {
    fun save(customer: Customer): Customer
    fun findById(id: Long): Customer
    fun delete(id: Long)
}