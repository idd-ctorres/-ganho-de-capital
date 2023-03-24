package operacoes

import modelo.Operacao
import modelo.Posicao

open class OperacaoDeVenda(
    posicao: Posicao,
    operacao: Operacao,
) : OperacaoFinanceira(posicao, operacao) {


    override fun calcular() {
        this.posicao.computarQuantidadeDeAcoesAtual(-this.operacao.quantidade)
        this.posicao.computarLucro(this.operacao)

        calcularImposto()
    }

    override fun podeCalcular(): Boolean {
        return this.operacao.isVenda()
    }
}