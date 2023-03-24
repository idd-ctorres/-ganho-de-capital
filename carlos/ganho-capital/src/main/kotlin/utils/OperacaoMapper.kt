package utils

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import modelo.Operacao
import java.util.*

object OperacaoMapper {
    private val MAPPER = ObjectMapper()
    fun obterOperacoesFromJson(json: String?): List<Operacao> {
        return try {
            listOf(*MAPPER.readValue(json, Array<Operacao>::class.java))
        } catch (e: JsonProcessingException) {
            throw RuntimeException(e)
        }
    }
}