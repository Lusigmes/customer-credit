package com.example.customercredit.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.jetbrains.annotations.NotNull

@Embeddable
data class Adress(
        @NotNull @Column(nullable = false)
        var street: String = "",
        @NotNull @Column(nullable = false)
        val zipCode: String = ""
)






