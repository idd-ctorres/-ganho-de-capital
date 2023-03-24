package operacoes

import modelo.Imposto
import modelo.Operacao
import modelo.Posicao

abstract class OperacaoFinanceira(open val posicao: Posicao, open val operacao: Operacao) {

    abstract fun calcular()

    open fun calcularImposto() {
        posicao.adicionarImposto(Imposto())
    }

    abstract fun podeCalcular(): Boolean

    companion object {
        fun processar(vararg operacoesFinanceiras: OperacaoFinanceira) {
            operacoesFinanceiras.firstOrNull { it.podeCalcular() }
                ?.calcular()
        }
    }
}