package model

import util.Logger
import util.NumberUtil
import java.math.BigDecimal

class OperationConsolidation {

    var averagePrice : BigDecimal = BigDecimal.ZERO
        private set

    var loss : BigDecimal = BigDecimal.ZERO
        private set

    var stockQuantity : BigDecimal = BigDecimal.ZERO
        private set

    // Consolida a média ponderada da operação
    fun consolidateBuyOperation(operation: Operation) : BigDecimal {

        if (operation.type != OperationType.BUY) {
            return averagePrice
        }

        val operationQuantity = operation.quantity.toBigDecimal()
        val unitCost = operation.unitCost.toBigDecimal()

        averagePrice = NumberUtil.round {
            if (averagePrice.compareTo(BigDecimal.ZERO) == 0) {
                unitCost
            } else {
                ((averagePrice * stockQuantity) + (operationQuantity * unitCost)) / (stockQuantity + operationQuantity)
            }
        }

        Logger.debug("Preço médio = $averagePrice")

        adjustStockQuantity(operation)

        return averagePrice
    }

    // Consolida os resultados da operação e retorna o lucro obtido
    fun consolidateSellOperation(operation: Operation) : BigDecimal {

        if (operation.type != OperationType.SELL) {
            return BigDecimal.ZERO
        }

        adjustStockQuantity(operation)

        val unitCost = operation.unitCost.toBigDecimal()
        val operationQuantity = operation.quantity.toBigDecimal()

        val results = NumberUtil.round { ((unitCost - averagePrice) * operationQuantity) }

        if (results > BigDecimal.ZERO) {
            var profit = results
            profit += loss

            if (profit > BigDecimal.ZERO) {
                loss = NumberUtil.round { BigDecimal.ZERO }
            } else {
                profit = BigDecimal.ZERO
                loss += results
            }

            Logger.debug("Resultado: $results")
            Logger.debug("Lucro: $profit")
            Logger.debug("Prejuízo Acumulado: $loss")

            return NumberUtil.round(profit)
        }

        loss = NumberUtil.round { loss + results }
        results.takeIf { results >= BigDecimal.ZERO }?.apply { Logger.debug("Lucro: $results") }
        Logger.debug("Prejuízo Acumulado: $loss")

        return NumberUtil.round { BigDecimal.ZERO }
    }

    // Consolida o total de ações da operação
    private fun adjustStockQuantity(operation: Operation) : BigDecimal {

        val operationQuantity = operation.quantity.toBigDecimal()

        stockQuantity = NumberUtil.round {
            if (operation.type == OperationType.BUY) {
                stockQuantity + operationQuantity
            } else {
                stockQuantity - operationQuantity
            }
        }

        Logger.debug("Quantidade de ações = $stockQuantity")

        return stockQuantity
    }

}