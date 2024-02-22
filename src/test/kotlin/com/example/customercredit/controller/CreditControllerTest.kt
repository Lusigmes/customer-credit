package com.example.customercredit.controller

import com.example.customercredit.dto.CreditDTO
import com.example.customercredit.model.entity.Credit
import com.example.customercredit.model.entity.Customer
import com.example.customercredit.model.enums.Status
import com.example.customercredit.repository.CreditRepository
import com.example.customercredit.service.CustomerServiceTest
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal
import java.time.LocalDate
import java.time.Month
import java.util.UUID

@SpringBootTest
@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class CreditControllerTest{
//        @Autowired private lateinit var creditController: CreditController
        @Autowired private lateinit var creditRepository: CreditRepository
        @Autowired private lateinit var mockMVC: MockMvc
        @Autowired private lateinit var objectMapper: ObjectMapper

        companion object{
            const val URL: String = "/api/credits"
        }

        @BeforeEach
        fun setup() = creditRepository.deleteAll()
        @AfterEach
        fun tearDown() = creditRepository.deleteAll()

        @Test
        fun `should save credit and return 201 status`() {
            //given
            val creditDTO: CreditDTO = buildCreditDTO()
            val values: String = objectMapper.writeValueAsString(creditDTO)
            //when//then
            mockMVC.perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(values))
                    .andExpect(MockMvcResultMatchers.status().isCreated)
                    .andExpect(MockMvcResultMatchers.jsonPath("$.creditValue").value(BigDecimal.valueOf(100)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.dayFirstInstallment").value(creditDTO.dayFirstInstallment.toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfInstallments").value(15))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(1L))
                    .andDo(MockMvcResultHandlers.print())
        }
    @Test
    fun `should find all credits by customer and return 200 status`() {
        //given
        val credit: Credit = creditRepository.save(buildCreditDTO().toEntity())
        //w0hen//then
        mockMVC.perform(
            MockMvcRequestBuilders.get("$URL/${credit.customer?.id}")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.creditValue").value(100))
            .andExpect(MockMvcResultMatchers.jsonPath("$.dayFirstInstallment").value(LocalDate.of(2024, Month.APRIL, 22).toString()))
            .andDo(MockMvcResultHandlers.print())
    }
    @Test
    fun `should find credit by code and return 200 status`() {

    }
    @Test
    fun `should not find credit by code and return 404 status`() {
        //given
        val invalidCode: UUID = UUID.randomUUID()
        val customerId: Long = 1
        val credit: CreditDTO = buildCreditDTO()
        val values: String = objectMapper.writeValueAsString(credit)
        //when//then
        mockMVC.perform(
            MockMvcRequestBuilders.get("${URL}/${invalidCode}?customerId=${customerId}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(values)
        ).andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request! Error"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(404))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class com.example.customercredit.exceptions.BusinessException")//class org.springframework.dao.DataIntegrityViolationException
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())

    }
        //@Test
        //fun `should not find credits with invalid customer ID and return 400 status`() {}
        //


        private fun buildCreditDTO(
              creditValue: BigDecimal = BigDecimal.valueOf(100),
              dayFirstInstallment: LocalDate = LocalDate.of(2024, Month.APRIL, 22),
              numberOfInstallments: Int = 15,
              customerId: Long = 1L,
        ): CreditDTO = CreditDTO(
              creditValue = creditValue,
              dayFirstInstallment = dayFirstInstallment,
              numberOfInstallments = numberOfInstallments,
              customerId = customerId,
        )


}