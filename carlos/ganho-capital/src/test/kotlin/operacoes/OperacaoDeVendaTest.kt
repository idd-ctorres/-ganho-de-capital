package operacoes

import modelo.Operacao
import modelo.Posicao
import modelo.TipoOperacaoEnum
import org.junit.jupiter.api.Assertions.*
import java.math.BigDecimal
import kotlin.test.Test

internal class OperacaoDeVendaTest {

    @Test
    fun deveCalcularTaxaDeComLucroDe10000() {
        val posicaoEmAberto = Posicao()
        val compra = Operacao(TipoOperacaoEnum.COMPRA, BigDecimal.valueOf(10.00), 10000)
        val venda = Operacao(TipoOperacaoEnum.VENDA, BigDecimal.valueOf(20.00), 5000)
        OperacaoDeCompra(posicaoEmAberto, compra).calcular()
        OperacaoDeVenda(posicaoEmAberto, venda).calcular()
        assertEquals(BigDecimal.ZERO.setScale(2), posicaoEmAberto.impostos.get(0).tax)
        assertEquals(BigDecimal.ZERO.setScale(2), posicaoEmAberto.impostos.get(1).tax)
    }
}