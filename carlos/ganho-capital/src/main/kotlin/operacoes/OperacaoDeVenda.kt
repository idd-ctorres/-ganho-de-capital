package operacoes

import modelo.Operacao
import modelo.Posicao
import java.math.BigDecimal
import java.math.RoundingMode

open class OperacaoDeVenda(
    posicao: Posicao,
    operacao: Operacao,
    protected var lucro: BigDecimal = BigDecimal.ZERO
) : OperacaoFinanceira(posicao, operacao) {


    override fun calcular() {
        this.posicao.computarQuantidadeDeAcoesAtual(-this.operacao.quantidade)

        lucro = operacao.custoUnitario.subtract(this.posicao.precoMedio)
            .multiply(BigDecimal.valueOf(operacao.quantidade)).setScale(2, RoundingMode.HALF_UP)

        val prejuizo = if (lucro.add(this.posicao.prejuizo)
                .compareTo(BigDecimal.ZERO) < 0
        ) this.posicao.prejuizo.add(
            lucro
        ) else BigDecimal.ZERO

        lucro = if (lucro.add(this.posicao.prejuizo)
                .compareTo(BigDecimal.ZERO) > 0
        ) lucro.add(this.posicao.prejuizo) else BigDecimal.ZERO

        this.posicao.prejuizo = prejuizo
        calcularImposto()
    }

    override fun podeCalcular(): Boolean {
        return this.operacao.isVenda()
    }
}