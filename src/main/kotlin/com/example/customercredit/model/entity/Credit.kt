package com.example.customercredit.model.entity

import com.example.customercredit.model.enums.Status
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import org.jetbrains.annotations.NotNull
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*


@Entity
@Table(name="credit")
@NoArgsConstructor
@AllArgsConstructor
data class Credit(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @Column(nullable = false, unique = true)
        val creditCode: UUID = UUID.randomUUID(),

        @Column(nullable = false)
        var creditValue: BigDecimal = BigDecimal.ZERO,

        @Column(nullable = false)
        var dayFirstInstallment: LocalDate,//var dayFirstInstallment: LocalDate = LocalDate.now(),

        @Column(nullable = false)
        var numberOfInstallments: Int = 0,

        @Enumerated(EnumType.STRING) @Column(nullable = false)
        var status: Status = Status.IN_PROGRESS,

        @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "customer_id")
        var customer: Customer? = null
)
