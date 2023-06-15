package com.idd.idd.service

import com.idd.idd.adapter.Adapter
import com.idd.idd.domain.Holder
import com.idd.idd.exception.HolderNotFoundException
import com.idd.idd.exception.InvalidCPFException
import com.idd.idd.exception.InvalidExternalIdException
import com.idd.idd.exception.InvalidPatchBodyException

import com.idd.idd.model.HolderPatchRequestDTO
import com.idd.idd.model.HolderPostRequestDTO
import com.idd.idd.model.HolderResponseDTO
import com.idd.idd.repository.HolderRepository
import com.idd.idd.util.CPFValidator
import com.idd.idd.util.HolValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class HolderService (

    @Autowired
    private val holderRepository: HolderRepository,

    @Autowired
    private val holderResponseAdapter: Adapter<Holder, HolderResponseDTO>,

    @Autowired
    private val holderRequestAdapter: Adapter<HolderPostRequestDTO, Holder>

) {
    fun createHolder(holderPostRequestDTO: HolderPostRequestDTO): HolderResponseDTO {
        if (!CPFValidator.isCpfValid(holderPostRequestDTO.cpf)) throw InvalidCPFException()

        val holder = holderRequestAdapter.adaptTo(holderPostRequestDTO)

        val savedHolder = holderRepository.save(holder)

        return holderResponseAdapter.adaptTo(savedHolder)
    }

    fun getHolder(externalId: String): HolderResponseDTO {
        if(!HolValidator.isHOLValid(externalId)) throw InvalidExternalIdException()

        val holder = holderRepository.findByExternalId(externalId) ?: throw HolderNotFoundException()

        return holderResponseAdapter.adaptTo(holder)
    }

    fun updateHolder(externalId: String, requestBody: HolderPatchRequestDTO): HolderResponseDTO {
        if (!HolValidator.isHOLValid(externalId)) throw InvalidExternalIdException()

        if (requestBody.firstName == null && requestBody.lastName == null && requestBody.cpf == null)
            throw InvalidPatchBodyException()

        val holder = holderRepository.findByExternalId(externalId) ?: throw HolderNotFoundException()

        holder.apply {
            firstName = requestBody.firstName ?: firstName
            lastName = requestBody.lastName ?: lastName
            cpf = requestBody.cpf ?: cpf
        }

        val updatedHolder = holderRepository.save(holder)

        return holderResponseAdapter.adaptTo(updatedHolder)
    }

    fun deleteHolder(externalId: String) {
        if (!HolValidator.isHOLValid(externalId)) throw InvalidExternalIdException()

        val holder = holderRepository.findByExternalId(externalId) ?: throw HolderNotFoundException()

        holderRepository.delete(holder)
    }

}