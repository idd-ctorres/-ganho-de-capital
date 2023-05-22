package com.idd.idd.repository

import com.idd.idd.domain.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository: JpaRepository<Account, Long> {
    fun findByHolderId(holderId: Long): Account?
}