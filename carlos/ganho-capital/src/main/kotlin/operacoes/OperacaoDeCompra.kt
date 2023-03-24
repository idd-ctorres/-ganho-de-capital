package operacoes

import modelo.Operacao
import modelo.Posicao
import java.math.BigDecimal
import java.math.RoundingMode

class OperacaoDeCompra(posicao: Posicao, operacao: Operacao) : OperacaoFinanceira(posicao, operacao) {
    override fun calcular() {
        this.posicao.precoMedio = this.posicao.getValor().add(this.operacao.getValor()).divide(
            BigDecimal.valueOf(this.operacao.quantidade + this.posicao.quantidadeDeAcoes),
            RoundingMode.HALF_UP)
        this.posicao.computarQuantidadeDeAcoesAtual(this.operacao.quantidade)
        this.calcularImposto()
    }

    override fun podeCalcular(): Boolean {
        return this.operacao.isCompra()
    }
}