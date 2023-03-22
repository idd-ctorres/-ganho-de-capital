package modelo

import java.math.BigDecimal

data class Posicao(var precoMedio: BigDecimal = BigDecimal.ZERO, var quantidadeDeAcoes: Long = 0, var prejuizo: BigDecimal = BigDecimal.ZERO, var impostos: MutableList<Imposto> = ArrayList()){

    fun computarQuantidadeDeAcoesAtual(quantidade: Long) {
        quantidadeDeAcoes += quantidade
    }

    fun adicionarImposto(imposto: Imposto) {
        impostos.add(imposto)
    }

    fun getValor(): BigDecimal {
        return precoMedio.multiply(BigDecimal.valueOf(quantidadeDeAcoes))
    }

}