package com.idd.idd.controller

import com.idd.idd.model.HolderPatchRequestDTO
import com.idd.idd.model.HolderPostRequestDTO
import com.idd.idd.model.HolderResponseDTO
import com.idd.idd.service.HolderService
import com.idd.idd.util.CPFValidator
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/holder")
class HolderController(private val holderService: HolderService) {

    @PostMapping("/{cpf}")
    fun validateCPF(@PathVariable cpf: String): Boolean = CPFValidator.isCpfValid(cpf)

    @PostMapping
    fun createHolder(@Valid @RequestBody holderPostRequestDTO: HolderPostRequestDTO): ResponseEntity<HolderResponseDTO> {
        val responseHolder = holderService.createHolder(holderPostRequestDTO)
        return ResponseEntity.ok(responseHolder)
    }

    @GetMapping("/{externalId}")
    fun getHolder(@PathVariable externalId: String): ResponseEntity<HolderResponseDTO> {
        val responseHolder: HolderResponseDTO = holderService.getHolder(externalId)
        return ResponseEntity.ok(responseHolder)
    }

    @PatchMapping("/{externalId}")
    fun updateHolder(@PathVariable externalId: String,
                    @Valid @RequestBody requestBody: HolderPatchRequestDTO): ResponseEntity<HolderResponseDTO> {
        val responseHolder =  holderService.updateHolder(externalId, requestBody)
        return ResponseEntity.ok(responseHolder)
    }

    @DeleteMapping("/{externalId}")
    fun deleteHolder(@PathVariable externalId: String): ResponseEntity<Any> {
        holderService.deleteHolder(externalId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}