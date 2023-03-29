package tax

import config.Config
import model.Operation
import model.OperationConsolidation
import model.OperationType
import util.Logger
import util.NumberUtil
import java.math.BigDecimal

class SellTaxCalculator : TaxCalculator {

    private val config = Config.getInstance()

    override fun handle(operation: Operation) : Boolean =
        operation.type == OperationType.SELL
    

    override fun calculate(operation: Operation, consolidation: OperationConsolidation) : Double {

        // Obtém o lucro da operação
        val profit = consolidation.consolidateSellOperation(operation)

        // Lucro mínimo para calcular a taxa
        val minOperation = config.tax.minOperation.toBigDecimal()

        // Percentual da taxa
        val taxPercentage = config.tax.percentage.toBigDecimal()

        val tax = profit.takeIf { profit >= minOperation }?.let {
            profit * ( taxPercentage / 100.toBigDecimal() )
        }?.let { NumberUtil.round(it) } ?: BigDecimal.ZERO

        Logger.debug("Taxa da operação: $tax")

        return tax.toDouble()
    }
}