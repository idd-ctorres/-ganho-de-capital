package operacoes

import modelo.Imposto
import modelo.Operacao
import modelo.Posicao
import java.math.BigDecimal
import java.math.RoundingMode

class OperacaoDeVendaAcimaDoLimiteDe20000(
    posicao: Posicao,
    operacao: Operacao
) : OperacaoDeVenda(posicao, operacao) {

    private val LIMITE_LUCRO = 20000L

    override fun calcularImposto() {
        if(this.posicao.lucro > BigDecimal.ZERO) {
            this.posicao.adicionarImposto(
                Imposto(
                    this.posicao.lucro.multiply(BigDecimal.valueOf(0.2))
                        .setScale(2, RoundingMode.HALF_UP)
                )
            )
            this.posicao.lucro = BigDecimal.ZERO
        }
        else
            super.calcularImposto()
    }

    override fun podeCalcular(): Boolean {
        return super.podeCalcular() && operacao.getValor()!! > BigDecimal.valueOf(LIMITE_LUCRO)
    }
}