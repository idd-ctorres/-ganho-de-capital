package com.idd.idd.controller

import com.github.fge.jsonpatch.JsonPatch
import com.idd.idd.model.HolderPatchRequestDTO
import com.idd.idd.model.HolderRequestDTO
import com.idd.idd.service.HolderService
import com.idd.idd.util.CPFValidator
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/holder")
class HolderController(private val holderService: HolderService) {

    @PostMapping("/{cpf}")
    fun validateCPF(@PathVariable cpf: String): Boolean = CPFValidator.isCpfValid(cpf)

    @PostMapping
    fun createHolder(@Valid @RequestBody holderRequestDTO: HolderRequestDTO): ResponseEntity<Any> =  holderService.createHolder(holderRequestDTO)

    @GetMapping("/{externalId}")
    fun getHolder(@PathVariable externalId: String): ResponseEntity<Any> =  holderService.getHolder(externalId)

    @PatchMapping("/{externalId}")
    fun updateHolder(@PathVariable externalId: String,
                    @Valid @RequestBody requestBody: HolderPatchRequestDTO): ResponseEntity<Any> {
        return holderService.updateHolder(externalId, requestBody)
    }

    @DeleteMapping("/{externalId}")
    fun deleteHolder(@PathVariable externalId: String): ResponseEntity<Any> = holderService.deleteHolder(externalId)
}