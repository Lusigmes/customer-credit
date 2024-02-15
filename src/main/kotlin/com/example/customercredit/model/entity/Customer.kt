package com.example.customercredit.model.entity

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import org.jetbrains.annotations.NotNull
import java.math.BigDecimal

@Entity
@Table(name="customer")
@NoArgsConstructor
@AllArgsConstructor
data class Customer(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @NotNull @Column(nullable = false)
        var firstName: String = "",

        @NotNull @Column(nullable = false)
        var lastName: String = "",

        @NotNull @Column(nullable = false, unique = true)
        val cpf: String,

        @NotNull @Column(nullable = false, unique = true)
        var email: String,

        @NotNull @Column(nullable = false)
        var password: String,

        @NotNull @Column(nullable = false) @Embedded
        var adress: Adress = Adress(),

        var income: BigDecimal,

        @Column(nullable = false)
        @OneToMany(fetch = FetchType.LAZY,
                mappedBy = "customer",
                cascade = [CascadeType.REMOVE])//cascade = arrayOf(CascadeType.REMOVE))
        var credits: List<Credit> = mutableListOf(),
)

