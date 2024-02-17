package com.example.customercredit.controller

import com.example.customercredit.dto.CreditDTO
import com.example.customercredit.model.entity.Credit
import com.example.customercredit.projections.CreditListProjection
import com.example.customercredit.projections.CreditProjection
import com.example.customercredit.service.impl.CreditService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import java.util.stream.Collectors


@RestController
@RequestMapping("/api/credits")
class CreditController(private val creditService: CreditService) {

    @PostMapping
    fun save(@RequestBody @Valid creditDTO: CreditDTO): ResponseEntity<String> {
        val creditSave: Credit = this.creditService.save(creditDTO.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Credit $creditSave - by customer: ${creditSave.customer?.firstName} saved.")
    }

    @GetMapping
    fun findAllByCustomer(@RequestParam(value="customerId") customerId: Long): ResponseEntity<List<CreditListProjection>>{
         val creditList: List<CreditListProjection> = this.creditService.findAllByCustomer(customerId).stream()
                .map { credit: Credit -> CreditListProjection(credit) }
                .collect(Collectors.toList())
        return ResponseEntity.status(HttpStatus.OK).body(creditList)

    }
    @GetMapping("/{creditCode}")
    fun findByCreditCode(@RequestParam(value="customerId") customerId: Long, @PathVariable creditCode: UUID): ResponseEntity<CreditProjection>{
        val credit: Credit = this.creditService.findByCreditCode(customerId,creditCode)
        return ResponseEntity.status(HttpStatus.OK).body(CreditProjection(credit))
    }

}