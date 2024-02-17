package com.example.customercredit.model.entity

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import java.math.BigDecimal

@Entity
@Table(name="customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
data class Customer(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @Column(nullable = false)
        var firstName: String = "",

        @Column(nullable = false)
        var lastName: String = "",

        @Column(nullable = false, unique = true)
        var cpf: String = "",

        @Column(nullable = false, unique = true)
        var email: String = "",

        @Column(nullable = false)
        var password: String = "",

        @Column(nullable = false) @Embedded
        var adress: Adress = Adress(),

        @Column(nullable = false)
        var income: BigDecimal = BigDecimal.ZERO,

        @Column(nullable = false)
        @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = [CascadeType.REMOVE])//cascade = arrayOf(CascadeType.REMOVE))
        var credits: List<Credit> = mutableListOf(),
){
        override fun toString(): String {
                return "Customer(id=$id, firstName='$firstName', lastName='$lastName', cpf='$cpf', email='$email', password='$password', income=$income)"
        }
}

