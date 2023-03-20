package file

import model.Operation
import model.OperationType
import java.io.ByteArrayOutputStream
import kotlin.test.Test
import kotlin.test.assertEquals

class StreamReadWriteJsonStrategyTest {

    @Test
    fun mustReadAJsonInputStreamToOperationModel() {

        val json = """{"operation":"buy", "unit-cost":10.00, "quantity": 10000}"""
        
        val operation = StreamReadWriteJsonStrategy().read<Operation>(json.toByteArray().inputStream())

        assertEquals(OperationType.BUY, operation.type)
        assertEquals(10.00, operation.unitCost)
        assertEquals(10000.0, operation.quantity)
    }

    @Test
    fun mustWriteOperationModelToAnOutputStream() {

        val expected = """{"operation":"sell","unit-cost":10.0,"quantity":10.0}"""

        val operation = Operation(OperationType.SELL, 10.00, 10.00)
        val outputStream = ByteArrayOutputStream()

        StreamReadWriteJsonStrategy().write(outputStream, operation)

        assertEquals(expected, outputStream.toString())
    }

}