package com.example.customercredit.service

import com.example.customercredit.exceptions.BusinessException
import com.example.customercredit.model.entity.Credit
import com.example.customercredit.model.entity.Customer
import com.example.customercredit.model.enums.Status
import com.example.customercredit.repository.CreditRepository
import com.example.customercredit.service.impl.CreditService
import com.example.customercredit.service.impl.CustomerService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@ActiveProfiles("test")
@ExtendWith(MockitoExtension::class)
class CreditServiceTest {
    @Mock
    lateinit var creditRepository: CreditRepository
    @Mock
    lateinit var customerService: CustomerService
    @InjectMocks
    lateinit var creditService: CreditService

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
//            MockitoAnnotations.initMocks(this)
//            creditService = CreditService(creditRepository,customerService)
    }

    @AfterEach
    fun tearDown() {

    }

    @Test
    fun `should create credit `() {
        //given
        val customerId: Long = 1L
        val credit: Credit = buildCredit()


        `when`(customerService.findById(customerId)).thenReturn(credit.customer)
        `when`(creditRepository.save(credit)).thenReturn(credit)
        //when
        val actual: Credit = this.creditRepository.save(credit)
        //then
        verify(customerService, times(1)).findById(customerId)
        verify(creditRepository, times(1)).save(credit)

        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameAs(credit)
        Assertions.assertThat(actual).isExactlyInstanceOf(Credit::class.java)
    }

    @Test
    fun `should not create credit when invalid day first installment`() {
        val invalidDay: LocalDate = LocalDate.now()
        val credit: Credit = buildCredit(dayFirstInstallment = invalidDay)
        `when`(creditRepository.save(credit)).thenAnswer { credit }
        //when // tratando exception
        Assertions.assertThatThrownBy { creditService.save(credit) }
            .isInstanceOf(BusinessException::class.java)
            .hasMessage("Invalid date")
        //then
        verify(creditRepository, times(0)).save(any())
    }

    @Test
    fun `should return list of credits for a customer`() {
        //given
        val customerId: Long = 1L
        val listCredits: List<Credit> = listOf(buildCredit(), buildCredit(), buildCredit())
        `when`(creditRepository.findAllByCustomerId(customerId)).thenReturn(listCredits)
//        when
        val actual: List<Credit> = creditService.findAllByCustomer(customerId)
        //then
        Assertions.assertThat(actual).isNotEmpty
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameAs(listCredits)
        Assertions.assertThat(actual).isExactlyInstanceOf(Credit::class.java)

        verify(creditRepository, times(1)).findAllByCustomerId(customerId)
    }

    @Test
    fun `should return credit for a valid customer and credit code`() {
        //given
        val customerId: Long = 1L
        val fakeCode: UUID = UUID.randomUUID()
        val credit: Credit = buildCredit(customer = Customer(id = customerId))

        `when`(creditRepository.findByCreditCode(fakeCode)).thenReturn(credit)
        //when
        val actual: Credit = creditService.findByCreditCode(customerId, fakeCode)
        //then
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameAs(credit)
        Assertions.assertThat(actual).isExactlyInstanceOf(Credit::class.java)

        verify(creditRepository, times(1)).findByCreditCode(fakeCode)
    }

    @Test
    fun `should throw BusinessException for invalid credit code`() {
        //given
        val customerId: Long = 1L
        val invalidCode: UUID = UUID.randomUUID()
        `when`(creditRepository.findByCreditCode(invalidCode)).thenReturn(null)
        //when // tratando exception
        Assertions.assertThatThrownBy { creditService.findByCreditCode(customerId, invalidCode) }
            .isInstanceOf(BusinessException::class.java)
            .hasMessage("Credit code $invalidCode not found")
        //then
        verify(creditRepository, times(0)).findByCreditCode(invalidCode)
    }

    @Test
    fun `should throw IllegalArgumentException for different customer ID`() {
        //givne
        val customerId: Long = 1L
        val invalidCode: UUID = UUID.randomUUID()
        val credit: Credit = buildCredit(customer = Customer(id = 2L))
        `when`(creditRepository.findByCreditCode(invalidCode)).thenReturn(credit)
        //when
        //then
        Assertions.assertThatThrownBy { creditService.findByCreditCode(customerId, invalidCode) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Bad Request! Error")
        verify(creditRepository).findByCreditCode(invalidCode)

    }
    companion object{
        private fun buildCredit(
            creditValue: BigDecimal = BigDecimal.valueOf(100.0),
            dayFirstInstallment: LocalDate = LocalDate.now().plusMonths(2L),
            numberOfInstallments: Int = 15,
            status: Status = Status.IN_PROGRESS,
            customer: Customer = CustomerServiceTest.buildCustomer(),
            id: Long = 1L
        ): Credit = Credit(
            creditValue = creditValue,
            dayFirstInstallment = dayFirstInstallment,
            numberOfInstallments = numberOfInstallments,
            status = status,
            customer = customer,
            id = id
        )
    }
}




