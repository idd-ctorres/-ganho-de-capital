package com.idd.idd.service


import com.fasterxml.jackson.databind.JsonNode
import com.github.fge.jsonpatch.JsonPatch
import com.fasterxml.jackson.databind.ObjectMapper
import com.idd.idd.domain.Holder
import com.idd.idd.model.HolderPatchRequestDTO
import com.idd.idd.model.HolderRequestDTO
import com.idd.idd.repository.HolderRepository
import com.idd.idd.util.CPFValidator
import com.idd.idd.util.HolValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service


@Service
class HolderService (

    @Autowired
    private val holderRepository: HolderRepository
) {
    fun createHolder(holderRequestDTO: HolderRequestDTO): ResponseEntity<Any> {
        if (!CPFValidator.isCpfValid(holderRequestDTO.cpf)) {
            val errorResponse = mapOf("error" to "Invalid CPF: ${holderRequestDTO.cpf}")
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
        }
         val holder = holderRepository.save(
            Holder(firstName = holderRequestDTO.firstName,
            lastName = holderRequestDTO.lastName,
            cpf = holderRequestDTO.cpf))

        val successResponse = mapOf("holder_external_id" to holder.externalId)
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse)
    }
    fun getHolder(externalId: String): ResponseEntity<Any>{
        if(!HolValidator.isHOLValid(externalId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to "Invalid external_id"))
        }

        val holder = holderRepository.findByExternalId(externalId) ?: return ResponseEntity.notFound().build()

        val successResponse = mapOf("holder_external_id" to holder.externalId,
                                    "first_name" to holder.firstName,
                                    "last_name" to holder.lastName)
        return ResponseEntity.status(HttpStatus.FOUND).body(successResponse)
    }

    fun updateHolder(externalId: String, requestBody: HolderPatchRequestDTO): ResponseEntity<Any> {
        if (!HolValidator.isHOLValid(externalId))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to "Invalid external_id"))

        if (requestBody.firstName == null && requestBody.lastName == null && requestBody.cpf == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to "Invalid incoming body"))

        val holder = holderRepository.findByExternalId(externalId) ?: return ResponseEntity.notFound().build()

        holder.apply {
            firstName = requestBody.firstName ?: firstName
            lastName = requestBody.lastName ?: lastName
            cpf = requestBody.cpf ?: cpf
        }

        val updatedHolder = holderRepository.save(holder)

        return ResponseEntity.ok().body(mapOf("external_id" to updatedHolder.externalId, "message" to "Holder updated" ))
    }

    fun deleteHolder(externalId: String): ResponseEntity<Any> {
        if (!HolValidator.isHOLValid(externalId))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to "Invalid external_id"))

        val holder = holderRepository.findByExternalId(externalId) ?: return ResponseEntity.notFound().build()

        holderRepository.delete(holder)

        return ResponseEntity.noContent().build()
    }

    private fun validateRequestBody(requestBody: HolderPatchRequestDTO): ResponseEntity<Any> {

//        val dtoProperties = HolderRequestDTO::class.java.declaredFields.map { it.name }
//        val requestBodyFields = requestBody.keys
//        val filteredProperties = dtoProperties.filter { requestBodyFields.contains(it) }
//
//        if (filteredProperties.isEmpty())
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to "Invalid fields in the request body"))

        return ResponseEntity.ok().build()
    }

    private fun updateHolderProperties(holder: Holder, requestBody: Map<String, String>) {
        val dtoProperties = HolderRequestDTO::class.java.declaredFields.map { it.name }
        val requestBodyFields = requestBody.keys
        val filteredProperties = dtoProperties.filter { requestBodyFields.contains(it) }

        for (property in filteredProperties) {
            val value = requestBody[property]
            when (property) {
                "firstName" -> holder.firstName = value.toString()
                "lastName" -> holder.lastName = value.toString()
                "cpf" -> holder.cpf = value.toString()
            }
        }
    }

    private fun applyPatchToHolder(patch: JsonPatch, targetHolder: Holder): Holder {
        val objectMapper = ObjectMapper()
        val patched = patch.apply(objectMapper.convertValue(targetHolder, JsonNode::class.java))
        return objectMapper.treeToValue(patched, Holder::class.java)
    }

}