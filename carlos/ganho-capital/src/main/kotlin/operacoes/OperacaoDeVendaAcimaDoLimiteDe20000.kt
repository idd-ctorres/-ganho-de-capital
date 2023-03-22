package operacoes

import modelo.Imposto
import modelo.Operacao
import modelo.Posicao
import java.math.BigDecimal

class OperacaoDeVendaAcimaDoLimiteDe20000(
    posicao: Posicao,
    operacao: Operacao
) : OperacaoDeVenda(posicao, operacao) {

    private val LIMITE_LUCRO = 20000L

    override fun calcularImposto() {
        this.posicao.adicionarImposto(Imposto(lucro.multiply(BigDecimal.valueOf(0.2))))
    }

    override fun podeCalcular(): Boolean {
        return super.podeCalcular() && operacao.getValor()!!
            .compareTo(BigDecimal.valueOf(LIMITE_LUCRO)) > 0
    }
}