package com.idd.idd.repository

import com.idd.idd.domain.Holder
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HolderRepository: JpaRepository<Holder, Long> {
    abstract fun findByExternalId(externalId: String): Holder?
}