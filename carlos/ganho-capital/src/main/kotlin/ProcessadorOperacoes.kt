import modelo.Operacao
import modelo.Posicao
import operacoes.OperacaoDeCompra
import operacoes.OperacaoDeVenda
import operacoes.OperacaoDeVendaAcimaDoLimiteDe20000
import operacoes.OperacaoFinanceira
import utils.ImpostoMapper
import utils.OperacaoMapper

class ProcessadorOperacoes {

    companion object {
        fun processarOperacoes(jsonOperacoes: String): String {
            val operacoes: List<Operacao> = OperacaoMapper.obterOperacoesFromJson(jsonOperacoes)
            val posicao = Posicao()

            operacoes.forEach { OperacaoFinanceira.processar(
                OperacaoDeCompra(posicao, it),
                OperacaoDeVendaAcimaDoLimiteDe20000(posicao, it),
                OperacaoDeVenda(posicao, it)
            )}

            return ImpostoMapper.toJson(posicao.impostos)
        }
    }

}