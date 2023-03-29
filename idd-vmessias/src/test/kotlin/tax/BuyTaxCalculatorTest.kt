package tax

import io.mockk.every
import io.mockk.mockk
import model.Operation
import model.OperationConsolidation
import model.OperationType
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BuyTaxCalculatorTest {

    private val buyTaxCalculator = BuyTaxCalculator()

    // Deve retornar true para o método handle quando OperationType == BUY
    @Test
    fun handleShouldReturnTrueForTypeBUY() {

        val operation = mockk<Operation>()

        every { operation.type } returns OperationType.BUY

        assertTrue { buyTaxCalculator.handle(operation) }
    }

    // Deve retornar false para o método handle quando OperationType != BUY
    @Test
    fun handleShouldReturnFalseForTypeDifferentThanBUY() {

        val operation = mockk<Operation>()

        every { operation.type } returns OperationType.SELL

        assertFalse { buyTaxCalculator.handle(operation) }
    }

    // Não deve calcular a taxa para OperationType == BUY
    @Test
    fun shouldntCalculateTaxForBuyOperation() {

        val operation = mockk<Operation>()
        val operationConsolidation = mockk<OperationConsolidation>(relaxed = true)

        assertEquals(0.00, buyTaxCalculator.calculate(operation, operationConsolidation))
    }

}