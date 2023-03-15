package tax

import model.OperationType
import java.math.BigDecimal

interface TaxCalculator {

    fun handle(operationType: OperationType) : Boolean

    fun calculate(operationType: OperationType, operationTotal: BigDecimal) : BigDecimal
}