package com.example.customercredit.repository

import com.example.customercredit.model.entity.Adress
import com.example.customercredit.model.entity.Credit
import com.example.customercredit.model.entity.Customer
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.time.Month
import java.util.*

//              TESTS FAILED
//unique repository with methods to test
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditRepositoryTest {
    @Autowired lateinit var creditRepository: CreditRepository
    @Autowired lateinit var testEntityManager: TestEntityManager

    private lateinit var customer: Customer
    private lateinit var credit1: Credit
    private lateinit var credit2: Credit

    @BeforeEach fun setup(){ // persistir no banco de dados
        customer = testEntityManager.persist(buildCustomer())
        credit1 = testEntityManager.persist(buildCredit(customer = customer))
        credit2 = testEntityManager.persist(buildCredit(customer = customer))
    }

    @Test
    fun `should find credit by credit code`(){
        //given
        val cCode1 = UUID.fromString("2046f7f6-52e3-4411-8d0a-aac01f78c812")
        val cCode2 = UUID.fromString("b05530aa-7f82-43c0-b0e7-67494c5a46a1")
        credit1.creditCode = cCode1
        credit2.creditCode = cCode2
        //when

        val fakeCredit1: Credit = creditRepository.findByCreditCode(cCode1)!!
        val fakeCredit2: Credit = creditRepository.findByCreditCode(cCode2)!!
        //then
        Assertions.assertThat(fakeCredit1).isNotNull
        Assertions.assertThat(fakeCredit2).isNotNull
        Assertions.assertThat(fakeCredit1).isSameAs(credit1)
        Assertions.assertThat(fakeCredit2).isSameAs(credit2)

    }

    @Test
    fun `should find all credits by customer id`(){
        //given
        val customerId: Long = 1L
        //when
        val creditList: List<Credit> = creditRepository.findAllByCustomerId(customerId)
        //then
        Assertions.assertThat(creditList).isNotEmpty
        Assertions.assertThat(creditList.size).isEqualTo(2)
        Assertions.assertThat(creditList).contains(credit1, credit2)

    }

    private fun buildCredit(
        creditValue: BigDecimal = BigDecimal.valueOf(500.0),
        dayFirstInstallment: LocalDate = LocalDate.of(2023, Month.APRIL, 22),
        numberOfInstallments: Int = 5,
        customer: Customer
    ): Credit = Credit(
        creditValue = creditValue,
        dayFirstInstallment = dayFirstInstallment,
        numberOfInstallments = numberOfInstallments,
        customer = customer
    )
    private fun buildCustomer(
        firstName: String = "Luis",
        lastName: String = "Gomes",
        cpf: String = "31297368592",
        email: String = "emnail@email.com",
        password: String = "string",
        zipCode: String = "1234",
        street: String = "Rua da luis",
        income: BigDecimal = BigDecimal.valueOf(0.0),
    ) = Customer(
        firstName = firstName,
        lastName = lastName,
        cpf = cpf,
        email = email,
        password = password,
        adress = Adress(
            zipCode = zipCode,
            street = street,
        ),
        income = income,
    )

}