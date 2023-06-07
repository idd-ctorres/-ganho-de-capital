package com.idd.idd.adapter

import com.idd.idd.domain.Account
import com.idd.idd.model.AccountResponseDTO
import org.springframework.stereotype.Component

@Component
class AccountResponseAdapter : Adapter<Account, AccountResponseDTO> {
    override fun adaptTo(source: Account): AccountResponseDTO {
        return AccountResponseDTO(
                number = source.number!!,
                branch = source.branch,
                holder = source.holder.externalId!!
        )
    }
}