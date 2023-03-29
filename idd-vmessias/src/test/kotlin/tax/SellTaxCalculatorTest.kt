package tax

import config.Config
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.OverrideMockKs
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import model.Operation
import model.OperationConsolidation
import model.OperationType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExtendWith(MockKExtension::class)
class SellTaxCalculatorTest {

    @MockK
    lateinit var config: Config

    @OverrideMockKs
    lateinit var sellTaxCalculator : SellTaxCalculator

    // Deve retornar true para o método handle quando OperationType == SELL
    @Test
    fun handleShouldReturnTrueForTypeSELL() {

        val operation = mockk<Operation>()

        every { operation.type } returns OperationType.SELL

        assertTrue { sellTaxCalculator.handle(operation) }
    }

    // Deve retornar false para o método handle quando OperationType != SELL
    @Test
    fun handleShouldReturnFalseForTypeDifferentThanSELL() {

        val operation = mockk<Operation>()

        every { operation.type } returns OperationType.BUY

        assertFalse { sellTaxCalculator.handle(operation) }
    }

    // Deve calcular a taxa quando o lucro é superior ao valor mínimo da operação
    @Test
    fun shouldCalculateTaxIfProfitAboveMinOperation() {

        val operation = mockk<Operation>()
        val operationConsolidation = mockk<OperationConsolidation>()

        every { operationConsolidation.consolidateSellOperation(eq(operation)) } returns BigDecimal(30000)
        every { config.tax.percentage } returns 10.0
        every { config.tax.minOperation } returns 29999.99

        assertEquals(3000.00, sellTaxCalculator.calculate(operation, operationConsolidation))
    }

    // Não deve calcular a taxa quando o lucro é inferior ao valor mínimo da operação
    @Test
    fun shouldntCalculateTaxIfProfitBelowMinOperation() {

        val operation = mockk<Operation>()
        val operationConsolidation = mockk<OperationConsolidation>()

        every { operationConsolidation.consolidateSellOperation(eq(operation)) } returns BigDecimal(30000)
        every { config.tax.percentage } returns 10.0
        every { config.tax.minOperation } returns 30000.01

        assertEquals(0.0, sellTaxCalculator.calculate(operation, operationConsolidation))
    }

}