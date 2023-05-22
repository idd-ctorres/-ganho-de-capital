package com.idd.idd.service

import com.idd.idd.domain.Account
import com.idd.idd.model.AccountDTO
import com.idd.idd.repository.AccountRepository
import com.idd.idd.repository.HolderRepository
import com.idd.idd.util.HolValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class AccountService(
    @Autowired
    private val accountRepository: AccountRepository,
    private val holderRepository: HolderRepository
){
    fun createAccount(holderExternalId: String): ResponseEntity<Any> {
        val holder = holderRepository.findByExternalId(holderExternalId)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to "Invalid holder"))

        // TODO: validate som kind of authorization linked to holder that I would have to implement in my application possibly jwt
        val createdAccount = accountRepository.save(Account(holder = holder))
        return ResponseEntity.status(HttpStatus.CREATED).body(mapOf("number" to createdAccount.number))
    }

    fun getAccountInfo(holderExternalId: String): ResponseEntity<Any> {

        val holder = holderRepository.findByExternalId(holderExternalId)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to "Invalid holder"))

        val account = accountRepository.findByHolderId(holder.id)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to "Holder does not have an account"))

        if (!account.isActive)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to "The requested account is closed"))

        val successResponse = mapOf("number" to account.number,
                                    "branch" to account.branch,
                                    "balance" to account.balance
            )
        return ResponseEntity.status(HttpStatus.FOUND).body(successResponse)
    }

    fun closeAccount(holderExternalId: String): ResponseEntity<Any> {

        val holder = holderRepository.findByExternalId(holderExternalId) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to "Invalid holder"))

        val account = accountRepository.findByHolderId(holder.id) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to "Holder does not have an account"))

        if (!account.isActive) return ResponseEntity.status(HttpStatus.CONFLICT).body(mapOf("error" to "The requested account is  already closed"))

        account.closeIt()

        accountRepository.save(account)

        return ResponseEntity.status(HttpStatus.OK).body(mapOf("message" to "Account closed"))
    }

}