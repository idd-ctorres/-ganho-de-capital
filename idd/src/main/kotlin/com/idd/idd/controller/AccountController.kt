package com.idd.idd.controller

import com.idd.idd.model.HolderRequestDTO
import com.idd.idd.service.AccountService
import jakarta.validation.Valid
import org.apache.coyote.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class AccountController (

    @Autowired
    private val accountService: AccountService
){
    @PostMapping("/account/{holderExternalId}/create")
    fun createAccount(@PathVariable holderExternalId: String): ResponseEntity<Any> = accountService.createAccount(holderExternalId)

    @GetMapping("/account/{holderExternalId}")
    fun getAccountInfo(@PathVariable holderExternalId: String): ResponseEntity<Any> {
        return accountService.getAccountInfo(holderExternalId)
    }
    @PatchMapping("/account/{holderExternalId}/close")
    fun closeAccount(@PathVariable holderExternalId: String): ResponseEntity<Any> {
        return accountService.closeAccount(holderExternalId)
    }
}