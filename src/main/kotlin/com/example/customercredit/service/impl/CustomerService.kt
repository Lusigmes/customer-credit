package com.example.customercredit.service.impl

import com.example.customercredit.model.entity.Customer
import com.example.customercredit.repository.CustomerRepository
import com.example.customercredit.service.ICustomerService
import org.springframework.stereotype.Service

@Service
class CustomerService(private val customerRepository: CustomerRepository): ICustomerService {
    override fun save(customer: Customer): Customer = this.customerRepository.save(customer)

    override fun findById(id: Long): Customer =
            this.customerRepository.findById(id).orElseThrow{
                throw Exception("ID $id not found")
            }

    override fun delete(id: Long) = this.customerRepository.deleteById(id)

}