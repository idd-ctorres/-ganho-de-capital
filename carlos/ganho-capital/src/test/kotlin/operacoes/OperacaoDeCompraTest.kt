package operacoes

import modelo.Operacao
import modelo.Posicao
import modelo.TipoOperacaoEnum
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class OperacaoDeCompraTest {

    private val compra = Operacao(TipoOperacaoEnum.COMPRA, BigDecimal.valueOf(10.00), 100)

    @Test
    fun deveCalcularTaxaDeUmaOperacaoERetornarZero() {
        val posicao = Posicao()
        val operacaoDeCompra = OperacaoDeCompra(posicao, compra)
        assertTrue(operacaoDeCompra.podeCalcular())
        operacaoDeCompra.calcular()
        assertEquals(BigDecimal.ZERO.setScale(2), posicao.impostos.get(0).tax)
    }

    @Test
    fun deveCalcularTaxaDeVariasOperacoesETodasRetornarZero() {
        val compra1 = Operacao(TipoOperacaoEnum.COMPRA, BigDecimal.valueOf(30.00), 140)
        val compra2 = Operacao(TipoOperacaoEnum.COMPRA, BigDecimal.valueOf(15.00), 120)
        val posicao = Posicao()
        listOf(compra1, compra2).forEach{ OperacaoDeCompra(posicao, it).calcular() }
        assertEquals(BigDecimal.ZERO.setScale(2), posicao.impostos.get(0).tax)
        assertEquals(BigDecimal.ZERO.setScale(2), posicao.impostos.get(1).tax)
    }
}