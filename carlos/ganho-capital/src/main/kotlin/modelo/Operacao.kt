package modelo

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

class Operacao(
    @JsonProperty("operation")
    val tipo: TipoOperacaoEnum? = null,

    @JsonProperty("unit-cost")
    val custoUnitario: BigDecimal = BigDecimal.ZERO,

    @JsonProperty("quantity")
    val quantidade: Long = 0
) {


    fun getValor(): BigDecimal? {
        return custoUnitario.multiply(BigDecimal.valueOf(quantidade))
    }

    fun isCompra(): Boolean {
        return tipo == TipoOperacaoEnum.COMPRA
    }

    fun isVenda(): Boolean {
        return tipo == TipoOperacaoEnum.VENDA
    }

}