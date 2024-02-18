package com.example.customercredit.service

import com.example.customercredit.model.entity.Credit
import com.example.customercredit.model.entity.Customer
import com.example.customercredit.repository.CreditRepository
import com.example.customercredit.service.impl.CreditService
import com.example.customercredit.service.impl.CustomerService
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.math.BigDecimal
import java.time.LocalDate

class CreditServiceTest {
//    @Test
//    fun `should create credit `() {}
//
//    @Test
//    fun `should not create credit when invalid day first installment`() {}
//
//    @Test
//    fun `should return list of credits for a customer`() {}
//
//    @Test
//    fun `should return credit for a valid customer and credit code`() {}
//
//    @Test
//    fun `should throw BusinessException for invalid credit code`() {}
//    @Test
//    fun `should throw IllegalArgumentException for different customer ID`() {}

    companion object {
        @Mock lateinit var creditRepository: CreditRepository
        @Mock lateinit var customerService: CustomerService
        @InjectMocks lateinit var creditService: CreditService

        @BeforeEach
        fun setup(){
            MockitoAnnotations.openMocks(this)
            MockitoAnnotations.initMocks(this)
        }

        @Test
        fun `should create credit`(){
            //given
            //when

            //then
        }

        private fun buildCredit(
            creditValue: BigDecimal = BigDecimal.valueOf(100.0),
            dayFirstInstallment: LocalDate = LocalDate.now().plusMonths(2L),
            numberOfInstallments: Int = 15,
            customer: Customer = CustomerServiceTest.buildCustomer()
        ): Credit = Credit(
            creditValue = creditValue,
            dayFirstInstallment = dayFirstInstallment,
            numberOfInstallments = numberOfInstallments,
            customer = customer
        )
    }
}