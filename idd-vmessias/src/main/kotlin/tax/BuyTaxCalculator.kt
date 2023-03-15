package tax

import model.OperationType
import java.math.BigDecimal

class BuyTaxCalculator : TaxCalculator {
    override fun handle(operationType: OperationType) : Boolean {
        return operationType == OperationType.BUY
    }

    override fun calculate(operationType: OperationType, operationTotal: BigDecimal): BigDecimal {
        return BigDecimal.ZERO
    }
}