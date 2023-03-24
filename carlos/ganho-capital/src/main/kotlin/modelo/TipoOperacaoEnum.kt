package modelo

import com.fasterxml.jackson.annotation.JsonProperty

enum class TipoOperacaoEnum {
    @JsonProperty("buy")
    COMPRA,
    @JsonProperty("sell")
    VENDA;
}