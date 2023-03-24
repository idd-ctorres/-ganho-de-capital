package utils

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import modelo.Imposto

object ImpostoMapper {
    private val MAPPER = ObjectMapper()
    fun toJson(impostos: List<Imposto?>?): String {
        return try {
            MAPPER.writeValueAsString(impostos)
        } catch (e: JsonProcessingException) {
            throw RuntimeException(e)
        }
    }
}