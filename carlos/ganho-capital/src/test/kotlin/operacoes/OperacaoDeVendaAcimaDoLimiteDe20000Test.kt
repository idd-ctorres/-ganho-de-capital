package operacoes

import modelo.Operacao
import modelo.Posicao
import modelo.TipoOperacaoEnum
import org.junit.jupiter.api.Assertions.*
import java.math.BigDecimal
import kotlin.test.Test

internal class OperacaoDeVendaAcimaDoLimiteDe20000Test {

    @Test
    fun deveCalcularTaxaDeComLucroDe80000() {
        val posicao = Posicao()
        val compra = Operacao(TipoOperacaoEnum.COMPRA, BigDecimal.valueOf(10.00), 10000)
        val venda = Operacao(TipoOperacaoEnum.VENDA, BigDecimal.valueOf(50.00), 10000)

        OperacaoDeCompra(posicao, compra).calcular()
        OperacaoDeVendaAcimaDoLimiteDe20000(posicao, venda).calcular()

        assertEquals(BigDecimal.ZERO.setScale(2), posicao.impostos[0].tax)
        assertEquals(BigDecimal.valueOf(80000.00).setScale(2), posicao.impostos[1].tax)
    }
}