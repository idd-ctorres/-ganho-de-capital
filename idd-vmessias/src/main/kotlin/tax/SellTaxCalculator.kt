package tax

import config.Config
import model.OperationType
import java.math.BigDecimal

class SellTaxCalculator : TaxCalculator {
    override fun handle(operationType: OperationType): Boolean {
        return operationType == OperationType.SELL
    }

    override fun calculate(operationType: OperationType, operationTotal: BigDecimal): BigDecimal {

        return Config.getInstance().tax.percentage.let {
            operationTotal.multiply((it / 100).toBigDecimal())
        }

    }
}