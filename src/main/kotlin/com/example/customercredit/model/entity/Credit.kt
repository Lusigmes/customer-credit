package com.example.customercredit.model.entity

import com.example.customercredit.model.enums.Status
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import java.math.BigDecimal
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*


@Entity
@Table(name="credit")
@Data
@NoArgsConstructor
@AllArgsConstructor
data class Credit(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @Column(nullable = false, unique = true)
        var creditCode: UUID = UUID.randomUUID(),

        @Column(nullable = false)
        var creditValue: BigDecimal = BigDecimal.ZERO,

        @Column(nullable = false)
        var dayFirstInstallment: LocalDate,

        @Column(nullable = false)
        var numberOfInstallments: Int = 0,

        @Enumerated(EnumType.STRING) @Column(nullable = false)
        var status: Status = Status.IN_PROGRESS,

        @ManyToOne @JoinColumn(name = "customer_id")
        var customer: Customer? = null
){

}
