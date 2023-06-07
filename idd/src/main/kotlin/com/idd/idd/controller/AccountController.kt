package com.idd.idd.controller

import com.idd.idd.model.AccountResponseDTO
import com.idd.idd.service.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class AccountController (

    @Autowired
    private val accountService: AccountService
){
    @PostMapping("/account/{holderExternalId}/create")
    fun createAccount(@PathVariable holderExternalId: String): ResponseEntity<AccountResponseDTO> {
        val responseAccount = accountService.createAccount(holderExternalId)
        return ResponseEntity.ok(responseAccount)
    }

    @GetMapping("/account/{holderExternalId}")
    fun getAccountInfo(@PathVariable holderExternalId: String): ResponseEntity<AccountResponseDTO> {
        val responseAccount = accountService.getAccountInfo(holderExternalId)
        return ResponseEntity.ok(responseAccount)
    }
    @PatchMapping("/account/{holderExternalId}/close")
    fun closeAccount(@PathVariable holderExternalId: String): ResponseEntity<Any> {
        accountService.closeAccount(holderExternalId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}