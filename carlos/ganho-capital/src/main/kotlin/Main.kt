import modelo.Operacao
import modelo.Posicao
import operacoes.*
import utils.ImpostoMapper
import utils.OperacaoMapper
import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)

    try {
        while (scanner.hasNextLine()) {
            val nextLine = scanner.nextLine()
            if (nextLine.isBlank()) break
            val operacoes: List<Operacao> = OperacaoMapper.obterOperacoesFromJson(nextLine)
            val posicaoEmAberto = Posicao()
            for (operacao in operacoes) {
                OperacaoFinanceira.processar(
                    OperacaoDeCompra(posicaoEmAberto, operacao),
                    OperacaoDeVendaAcimaDoLimiteDe20000(posicaoEmAberto, operacao),
                    OperacaoDeVenda(posicaoEmAberto, operacao)
                )
            }
            println(ImpostoMapper.toJson(posicaoEmAberto.impostos))
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        scanner.close()
    }
}