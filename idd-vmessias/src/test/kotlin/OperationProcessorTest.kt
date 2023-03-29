import io.mockk.every
import io.mockk.impl.annotations.OverrideMockKs
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import model.Operation
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import tax.TaxCalculator
import java.lang.RuntimeException
import kotlin.test.Test
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class OperationProcessorTest {

    @SpyK
    var strategies : ArrayList<TaxCalculator> = arrayListOf()

    @OverrideMockKs
    var operationProcessor = OperationProcessor()

    @Test
    fun processShouldCalculateResults() {

        val taxCalculator : TaxCalculator = mockk()
        val operation1 : Operation = mockk()
        val operation2 : Operation = mockk()
        val operations = listOf(operation1, operation2)

        strategies.add(taxCalculator)

        every { taxCalculator.calculate(eq(operation1), any()) } returns 20.00
        every { taxCalculator.handle(eq(operation1)) } returns true

        every { taxCalculator.calculate(eq(operation2), any()) } returns 10.00
        every { taxCalculator.handle(eq(operation2)) } returns true

        val process = operationProcessor.process(operations)

        assertEquals(20.00, process.elementAt(0).tax)
        assertEquals(10.00, process.elementAt(1).tax)
    }

    @Test
    fun processShouldThrowOnInvalidOperationType() {

        val taxCalculator : TaxCalculator = mockk()
        val operation1 : Operation = mockk()
        val operations = listOf(operation1)

        strategies.add(taxCalculator)

        every { taxCalculator.handle(eq(operation1)) } returns false

        assertThrows<RuntimeException> { operationProcessor.process(operations) }
    }

}