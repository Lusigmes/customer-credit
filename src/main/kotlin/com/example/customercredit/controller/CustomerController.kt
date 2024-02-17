package com.example.customercredit.controller

import com.example.customercredit.dto.CustomerDTO
import com.example.customercredit.dto.CustomerUpdateDTO
import com.example.customercredit.model.entity.Customer
import com.example.customercredit.projections.CustomerProjection
import com.example.customercredit.service.impl.CustomerService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/customers")
class CustomerController(private val customerService: CustomerService) {

    @PostMapping
    fun save(@RequestBody @Valid customerDTO: CustomerDTO): ResponseEntity<String>{
        val customerSave = this.customerService.save(customerDTO.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer ${customerSave.email} saved.")
    }

    @GetMapping("/{customerId}")
    fun findById(@PathVariable customerId: Long): ResponseEntity<CustomerProjection>{
        val customer: Customer = this.customerService.findById(customerId)
        return ResponseEntity.status(HttpStatus.OK).body(CustomerProjection(customer))
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable customerId: Long) = this.customerService.delete(customerId)
    
    @PatchMapping
    fun update(@RequestParam(value = "customerId") customerId: Long,
               @RequestBody @Valid customerUpdateDTO: CustomerUpdateDTO): ResponseEntity<CustomerProjection>{

        val customer: Customer = this.customerService.findById(customerId)
        val customerToUpdate: Customer = customerUpdateDTO.toEntity(customer)
        val customerUpdated: Customer = this.customerService.save(customerToUpdate)

        return ResponseEntity.status(HttpStatus.OK).body(CustomerProjection(customerUpdated))
    }
}