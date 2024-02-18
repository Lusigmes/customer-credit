package com.example.customercredit.controller

import com.example.customercredit.dto.CustomerDTO
import com.example.customercredit.dto.CustomerUpdateDTO

import com.example.customercredit.model.entity.Customer
import com.example.customercredit.repository.CustomerRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal
import java.util.Random


//do creditControllerTest
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class CustomerControllerTest {
    @Autowired private lateinit var customerRepository: CustomerRepository
    @Autowired private lateinit var mockMvc: MockMvc
    @Autowired private lateinit var  objectMapper: ObjectMapper


    companion object{
        const val URL: String = "/api/customers"
    }
    @BeforeEach fun setup() = customerRepository.deleteAll()
    @BeforeEach fun tearDown() = customerRepository.deleteAll()
    @Test
    fun `should create a customer and return 201 status`(){
        //given
        val customerDTO: CustomerDTO = buildCustomerDTO()
        val value: String = objectMapper.writeValueAsString(customerDTO)
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(value))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Luis"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Gomes"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("31297368592"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("email@email.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.income").value("0.0"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("12345"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Rua da luis"))
//            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(37))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should not save customer with same CPF and return 409 status`(){
        //given
        customerRepository.save(buildCustomerDTO().toEntity())
        val customerDTO: CustomerDTO = buildCustomerDTO()
        val values: String = objectMapper.writeValueAsString(customerDTO)
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(values))
            .andExpect(MockMvcResultMatchers.status().isConflict)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Conflict! Error"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(409))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("DataIntegrityViolationException")//class org.springframework.dao.DataIntegrityViolationException
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }


    @Test
    fun `should not save customer with firstName empty and return 400 status`(){
        //given
        val customerDTO: CustomerDTO = buildCustomerDTO(firstName = "")
        val values: String = objectMapper.writeValueAsString(customerDTO)
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(values))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request! Error"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class org.springframework.web.bind.MethodArgumentNotValidException")//class org.springframework.dao.DataIntegrityViolationException
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should find customer by id and return 200 status`(){
        //given
        val customer: Customer = customerRepository.save(buildCustomerDTO().toEntity())
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.get("$URL/${customer.id}")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Luis"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Gomes"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("31297368592"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("email@email.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.income").value("0.0"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("12345"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Rua da luis"))
//            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(37))
            .andDo(MockMvcResultHandlers.print())


    }



    @Test
    fun `should not find customer with invalid id and return 400 status`(){
        //given
        val invalidId: Long = 2L
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.get("$URL/$invalidId")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request! Error"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class com.example.customercredit.exceptions.BusinessException")//class org.springframework.dao.DataIntegrityViolationException
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }
    @Test
    fun `should delete customer by id and return 204 status`(){
        //given
        val customer: Customer = customerRepository.save(buildCustomerDTO().toEntity())
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.delete("$URL/${customer.id}")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent)
            .andDo(MockMvcResultHandlers.print())

    }
        @Test
    fun `should not delete by id and return 400 status`(){
        //given
        val invalidID: Long = Random().nextLong()
        //when
        //then
            mockMvc.perform(
                MockMvcRequestBuilders.delete("$URL/$invalidID")
                    .accept(MediaType.APPLICATION_JSON)
            )
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request! Error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(
                    MockMvcResultMatchers.jsonPath("$.exception")
                        .value("class com.example.customercredit.exceptions.BusinessException")//class org.springframework.dao.DataIntegrityViolationException
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
                .andDo(MockMvcResultHandlers.print())
    }
    @Test
    fun `should update customer and return 200 status`(){

        val customer: Customer = customerRepository.save(buildCustomerDTO().toEntity())
        val customerUpdateDTO: CustomerUpdateDTO = buildCustomerUpdateDto()
        val value: String = objectMapper.writeValueAsString(customerUpdateDTO)
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.patch("$URL?customerId=${customer.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(value)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Luis Update"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Gomes Update"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("31297368592"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("email@email.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.income").value("5000.0"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("45656"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Rua do Luis"))
//            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(37))
            .andDo(MockMvcResultHandlers.print())

    }
    @Test
    fun `should not update customer with invalid id and return 400 status`(){
        //given
        val invalidId: Long = Random().nextLong()
        val customerUpdateDTO: CustomerUpdateDTO = buildCustomerUpdateDto()
        val value: String = objectMapper.writeValueAsString(customerUpdateDTO)
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.patch("$URL?customerId=${invalidId}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(value)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request! Error"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class com.example.customercredit.exceptions.BusinessException")//class org.springframework.dao.DataIntegrityViolationException
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    private fun buildCustomerDTO(
        firstName: String = "Luis",
        lastName: String = "Gomes",
        cpf: String = "31297368592",
        email: String = "email@email.com",
        password: String = "1234",
        zipCode: String = "12345",
        street: String = "Rua da luis",
        income: BigDecimal = BigDecimal.valueOf(0.0),
    ) = CustomerDTO(
        firstName = firstName,
        lastName = lastName,
        cpf = cpf,
        email = email,
        password = password,
        zipCode = zipCode,
        street = street,
        income = income,
    )
    private fun buildCustomerUpdateDto(
        firstName: String = "Luis Update",
        lastName: String = "Gomes Update",
        income: BigDecimal = BigDecimal.valueOf(5000.0),
        zipCode: String = "45656",
        street: String = "Rua do Luis"
    ): CustomerUpdateDTO = CustomerUpdateDTO(
        firstName = firstName,
        lastName = lastName,
        income = income,
        zipCode = zipCode,
        street = street
    )
}