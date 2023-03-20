package model

import io.mockk.every
import io.mockk.impl.annotations.OverrideMockKs
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class OperationConsolidationTest {

    private val operationConsolidation = OperationConsolidation()

    @Test
    fun shouldConsolidateBuyOperationWhenOperationTypeIsBUY() {

        val operation = mockk<Operation>()

        every { operation.type } returns OperationType.BUY
        every { operation.quantity } returns 10.0
        every { operation.unitCost } returns 20.0

        var weightedAverage = operationConsolidation.consolidateBuyOperation(operation)

        assertEquals(BigDecimal(20).setScale(2), weightedAverage)
        assertEquals(BigDecimal(10).setScale(2), operationConsolidation.stockQuantity.setScale(2))

        every { operation.quantity } returns 10.0
        every { operation.unitCost } returns 10.0

        weightedAverage = operationConsolidation.consolidateBuyOperation(operation)

        assertEquals(BigDecimal(15).setScale(2), weightedAverage)
        assertEquals(BigDecimal(20).setScale(2), operationConsolidation.stockQuantity.setScale(2))
    }

    @Test
    fun shouldntConsolidateBuyOperationWhenOperationTypeIsNotBUY() {

        val operation = mockk<Operation>()

        every { operation.type } returns OperationType.BUY
        every { operation.quantity } returns 10.0
        every { operation.unitCost } returns 20.0

        var weightedAverage = operationConsolidation.consolidateBuyOperation(operation)

        assertEquals(BigDecimal(20).setScale(2), weightedAverage)
        assertEquals(BigDecimal(10).setScale(2), operationConsolidation.stockQuantity.setScale(2))

        every { operation.type } returns OperationType.SELL
        every { operation.quantity } returns 10.0
        every { operation.unitCost } returns 10.0

        weightedAverage = operationConsolidation.consolidateBuyOperation(operation)

        // Média deve permanecer a mesma
        assertEquals(BigDecimal(20).setScale(2), weightedAverage)
        // Quantidade de ações deve permanecer a mesma
        assertEquals(BigDecimal(10).setScale(2), operationConsolidation.stockQuantity.setScale(2))
    }

    @Test
    fun shouldConsolidateSellOperationWhenOperationTypeIsSELL() {

        val operation = mockk<Operation>()

        every { operation.type } returns OperationType.BUY
        every { operation.quantity } returns 10.0
        every { operation.unitCost } returns 20.0

        // Preço médio = 20
        operationConsolidation.consolidateBuyOperation(operation)

        every { operation.type } returns OperationType.SELL
        every { operation.quantity } returns 5.0
        every { operation.unitCost } returns 10.0

        // Operação de prejuízo
        var profit = operationConsolidation.consolidateSellOperation(operation)

        assertEquals(BigDecimal(5).setScale(2), operationConsolidation.stockQuantity)
        assertEquals(BigDecimal(-50).setScale(2), operationConsolidation.loss)
        assertEquals(BigDecimal.ZERO.setScale(2), profit)

        every { operation.type } returns OperationType.BUY
        every { operation.quantity } returns 20.0
        every { operation.unitCost } returns 10.0

        // Preço médio = 12
        operationConsolidation.consolidateBuyOperation(operation)

        every { operation.type } returns OperationType.SELL
        every { operation.quantity } returns 10.0
        every { operation.unitCost } returns 14.0

        // Operação de prejuízo
        profit = operationConsolidation.consolidateSellOperation(operation)

        assertEquals(BigDecimal(15).setScale(2), operationConsolidation.stockQuantity)
        assertEquals(BigDecimal(-30).setScale(2), operationConsolidation.loss)
        assertEquals(BigDecimal.ZERO.setScale(2), profit)

        every { operation.type } returns OperationType.SELL
        every { operation.quantity } returns 15.0
        every { operation.unitCost } returns 15.0

        // Operação de lucro
        profit = operationConsolidation.consolidateSellOperation(operation)

        assertEquals(BigDecimal(0).setScale(2), operationConsolidation.stockQuantity)
        assertEquals(BigDecimal.ZERO.setScale(2), operationConsolidation.loss)
        assertEquals(BigDecimal(15).setScale(2), profit)
    }

    @Test
    fun shouldntConsolidateSellOperationWhenOperationTypeIsntSELL() {

        val operation = mockk<Operation>()

        every { operation.type } returns OperationType.BUY
        every { operation.quantity } returns 10.0
        every { operation.unitCost } returns 20.0

        // Preço médio = 20
        operationConsolidation.consolidateBuyOperation(operation)

        every { operation.type } returns OperationType.BUY
        every { operation.quantity } returns 5.0
        every { operation.unitCost } returns 10.0

        // Operação de prejuízo
        val profit = operationConsolidation.consolidateSellOperation(operation)

        assertEquals(BigDecimal(10).setScale(2), operationConsolidation.stockQuantity)
        assertEquals(BigDecimal.ZERO, operationConsolidation.loss)
        assertEquals(BigDecimal.ZERO, profit)
    }

}