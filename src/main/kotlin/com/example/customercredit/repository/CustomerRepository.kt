package com.example.customercredit.repository

import com.example.customercredit.model.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.stereotype.Repository

@RepositoryRestResource(collectionResourceRel = "customers", path = "customers")
@Repository
interface CustomerRepository: JpaRepository<Customer, Long> {
}