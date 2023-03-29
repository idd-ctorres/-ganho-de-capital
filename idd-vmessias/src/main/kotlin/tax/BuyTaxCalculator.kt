package tax

import model.Operation
import model.OperationConsolidation
import model.OperationType
import util.Logger
import java.math.BigDecimal

class BuyTaxCalculator : TaxCalculator {
    override fun handle(operation: Operation) : Boolean {
        return operation.type == OperationType.BUY
    }

    override fun calculate(operation: Operation, consolidation: OperationConsolidation) : Double {

        // Consolida a média ponderada
        consolidation.consolidateBuyOperation(operation)

        // Taxa zero para compra
        val tax = BigDecimal.ZERO

        Logger.debug("Taxa da operação = $tax")

        return tax.toDouble()
    }
}