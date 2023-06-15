package com.idd.idd.adapter

import com.idd.idd.domain.Holder
import com.idd.idd.model.HolderResponseDTO
import org.springframework.stereotype.Component

@Component
class HolderResponseAdapter : Adapter<Holder, HolderResponseDTO> {
    override fun adaptTo(source: Holder): HolderResponseDTO {
        return HolderResponseDTO(
                externalId = source.externalId!!,
                firstName = source.firstName,
                lastName = source.lastName)
    }

}