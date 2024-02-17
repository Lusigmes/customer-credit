package com.example.customercredit.service

import com.example.customercredit.exceptions.BusinessException
import com.example.customercredit.model.entity.Adress
import com.example.customercredit.model.entity.Customer
import com.example.customercredit.repository.CustomerRepository
import com.example.customercredit.service.impl.CustomerService
import org.assertj.core.api.Assertions

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*

import org.mockito.junit.jupiter.MockitoExtension

import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.util.Optional
import java.util.Random

@ActiveProfiles("test")
@ExtendWith(MockitoExtension::class)//MockKExtension::class
class CustomerServiceTest {
    @Mock lateinit var customerRepository: CustomerRepository//@MockK
    @InjectMocks lateinit var customerService: CustomerService//@InjectMockKs
    @Test
    fun `should create customer`(){
        //given
        val fakeCustomer: Customer = buildCustomer()
        `when`(customerRepository.save(fakeCustomer)).thenReturn(fakeCustomer) //every{customerRepository.save(fakeCustomer)} returns Optional.of(fakeCustomer)
        //when
        val actual: Customer = customerService.save(fakeCustomer)
        //then
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isExactlyInstanceOf(Customer::class.java)
        Assertions.assertThat(actual).isSameAs(fakeCustomer)
        verify(customerRepository, times(1)).save(fakeCustomer) //verify(exactly = 1) { customerRepository.save(fakeCustomer) }
    }
    @Test
    fun `should find customer by id`() {
        //given
        val fakeId: Long = Random().nextLong()
        val fakeCustomer: Customer = buildCustomer(id = fakeId)
        `when`(customerRepository.findById(fakeId)).thenReturn(Optional.of(fakeCustomer))//
        //when
        val actual: Customer = customerService.findById(fakeId)
        //then
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isExactlyInstanceOf(Customer::class.java)
        Assertions.assertThat(actual).isSameAs(fakeCustomer)
        verify(customerRepository, times(1)).findById(fakeId)//
    }
    @Test
    fun `should not find customer by invalid id and throw BusinessException`() {
        //given
        val fakeId: Long = Random().nextLong()
        `when`(customerRepository.findById(fakeId)).thenReturn(Optional.empty())//every { customerRepository.findById(fakeId) } returns Optional.empty()
        //when
        //then
        Assertions.assertThatExceptionOfType(BusinessException::class.java)
            .isThrownBy { customerService.findById(fakeId) }
            .withMessage("ID $fakeId not found")
        verify(customerRepository, times(1)).findById(fakeId)//verify(exactly = 1) { customerRepository.findById(fakeId)   }
    }
    @Test
    fun `should delete customer by id`() {
        //given
        val fakeId: Long = Random().nextLong()
        val fakeCustomer: Customer = buildCustomer(id = fakeId)
        `when`(customerRepository.findById(fakeId)).thenReturn(Optional.of(fakeCustomer))//every { customerRepository.findById(fakeId) } returns Optional.of(fakeCustomer)
        doNothing().`when`(customerRepository).delete(fakeCustomer)//every { customerRepository.delete(fakeCustomer) } just runs
        //when
        customerService.delete(fakeId)
        //then
        verify(customerRepository, times(1)).findById(fakeId)//verify(exactly = 1) { customerRepository.findById(fakeId) }
        verify(customerRepository, times(1)).delete(fakeCustomer)//verify(exactly = 1) { customerRepository.delete(fakeCustomer) }
    }
    companion object{
        private fun buildCustomer(
            firstName: String ="Luis",
            lastName: String = "Gomes",
            cpf: String = "41676492178",
            email: String = "luis@email.com",
            password: String = "123",
            zipCode: String ="123456",
            street: String = "Rua",
            income: BigDecimal = BigDecimal.valueOf(1000.0),
            id: Long = 1L
        ) = Customer(
            firstName= firstName,
            lastName = lastName,
            cpf = cpf,
            email = email,
            password = password,
            adress = Adress(
                zipCode = zipCode,
                street= street
            ),
            income = income,
            id = id
        )
    }

}






