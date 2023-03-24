package operacoes

import modelo.Operacao
import modelo.Posicao
import modelo.TipoOperacaoEnum
import org.junit.jupiter.api.Assertions.*
import java.math.BigDecimal
import kotlin.test.Test

internal class OperacaoDeVendaTest {

    @Test
    fun deveCalcularTaxaDeComLucroDe50000() {
        val posicao = Posicao()
        val compra = Operacao(TipoOperacaoEnum.COMPRA, BigDecimal.valueOf(10.00), 10000)
        val venda = Operacao(TipoOperacaoEnum.VENDA, BigDecimal.valueOf(20.00), 5000)
        OperacaoDeCompra(posicao, compra).calcular()
        OperacaoDeVenda(posicao, venda).calcular()
        assertEquals(BigDecimal.ZERO.setScale(2), posicao.impostos[0].tax)
        assertEquals(BigDecimal.ZERO.setScale(2), posicao.impostos[1].tax)
        assertEquals(BigDecimal.valueOf(50000).setScale(2), posicao.getValor())
    }
}