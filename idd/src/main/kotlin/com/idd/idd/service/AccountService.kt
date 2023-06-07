package com.idd.idd.service

import com.idd.idd.adapter.Adapter
import com.idd.idd.domain.Account
import com.idd.idd.exception.AccountClosedException
import com.idd.idd.exception.AccountNotFoundException
import com.idd.idd.exception.HolderNotFoundException
import com.idd.idd.model.AccountResponseDTO
import com.idd.idd.repository.AccountRepository
import com.idd.idd.repository.HolderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AccountService(
    @Autowired
    private val accountRepository: AccountRepository,
    @Autowired
    private val holderRepository: HolderRepository,
    @Autowired
    private val accountResponseAdapter: Adapter<Account, AccountResponseDTO>
){
    fun createAccount(holderExternalId: String): AccountResponseDTO {
        val holder = holderRepository.findByExternalId(holderExternalId) ?: throw HolderNotFoundException()

        val createdAccount = accountRepository.save(Account(holder = holder))

        return accountResponseAdapter.adaptTo(createdAccount)
    }

    fun getAccountInfo(holderExternalId: String): AccountResponseDTO {

        val holder = holderRepository.findByExternalId(holderExternalId) ?: throw HolderNotFoundException()

        val accountFound = accountRepository.findByHolderId(holder.id) ?: throw AccountNotFoundException()

        if (!accountFound.isActive) throw AccountClosedException()

        return accountResponseAdapter.adaptTo(accountFound)
    }

    fun closeAccount(holderExternalId: String) {

        val holder = holderRepository.findByExternalId(holderExternalId) ?: throw HolderNotFoundException()

        val accountFound = accountRepository.findByHolderId(holder.id) ?: throw AccountNotFoundException()

        if (!accountFound.isActive) throw AccountClosedException()

        accountFound.closeIt()

        accountRepository.save(accountFound)

    }

}