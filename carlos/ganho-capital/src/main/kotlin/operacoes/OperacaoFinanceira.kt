package operacoes

import modelo.Imposto
import modelo.Operacao
import modelo.Posicao
import java.util.stream.Stream

abstract class OperacaoFinanceira(open val posicao: Posicao, open val operacao: Operacao) {

    abstract fun calcular()

    open fun calcularImposto() {
        posicao.adicionarImposto(Imposto())
    }

    abstract fun podeCalcular(): Boolean

    companion object {
        fun processar(vararg operacoesFinanceiras: OperacaoFinanceira) {
            operacoesFinanceiras
                .filter { it.podeCalcular() }
                .firstOrNull()
                ?.calcular()
        }
    }
}