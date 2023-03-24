package modelo

import java.math.BigDecimal
import java.math.RoundingMode

data class Posicao(var precoMedio: BigDecimal = BigDecimal.ZERO, var quantidadeDeAcoes: Long = 0, var lucro: BigDecimal = BigDecimal.ZERO, var impostos: MutableList<Imposto> = ArrayList()){

    fun computarQuantidadeDeAcoesAtual(quantidade: Long) {
        quantidadeDeAcoes += quantidade
    }

    fun adicionarImposto(imposto: Imposto) {
        impostos.add(imposto)
    }

    fun getValor(): BigDecimal {
        return precoMedio.multiply(BigDecimal.valueOf(quantidadeDeAcoes)).setScale(2, RoundingMode.HALF_UP)
    }

    fun computarLucro(operacao: Operacao) {
        this.lucro += operacao.custoUnitario.subtract(this.precoMedio)
            .multiply(BigDecimal.valueOf(operacao.quantidade)).setScale(2, RoundingMode.HALF_UP)
    }

}