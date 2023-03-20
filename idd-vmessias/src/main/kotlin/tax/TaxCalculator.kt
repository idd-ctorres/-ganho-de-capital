package tax

import model.Operation
import model.OperationConsolidation
import java.math.BigDecimal

interface TaxCalculator {

    fun handle(operation: Operation) : Boolean

    fun calculate(operation: Operation, consolidation: OperationConsolidation) : Double
}