package com.example.customercredit.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime

data class BusinessException(override val message: String?): RuntimeException(message) {
}