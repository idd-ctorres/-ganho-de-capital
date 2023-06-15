package com.idd.idd.adapter

import com.idd.idd.domain.Holder
import com.idd.idd.model.HolderPostRequestDTO
import org.springframework.stereotype.Component

@Component
class HolderRequestAdapter: Adapter<HolderPostRequestDTO, Holder> {
    override fun adaptTo(source: HolderPostRequestDTO): Holder {
        return Holder(
                firstName = source.firstName,
                lastName = source.lastName,
                cpf = source.cpf)
    }
}