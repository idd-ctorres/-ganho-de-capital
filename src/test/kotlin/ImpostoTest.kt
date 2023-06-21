import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class ImpostoTest {

    @Test
    fun testSalarioIsento(){
        val input = "1500.00"
        val expectedOutput = "Coloque o valor do salário em R\$ seguindo o padrão 3002.00: \n" +
                "O salário é: 1500,00\n" + "Isento de taxas.\n"
        assertProgramOutput(input, expectedOutput)
    }

    @Test
    fun testSalario8Percent(){
        val input = "2500.00"
        val expectedOutput = "Coloque o valor do salário em R\$ seguindo o padrão 3002.00: \n" +
                "O salário é: 2500,00\n" + "O valor a ser pago de imposto é: 40,00\n"
        assertProgramOutput(input, expectedOutput)
    }

    @Test
    fun testSalario18Percent(){
        val input = "4000.00"
        val expectedOutput = "Coloque o valor do salário em R\$ seguindo o padrão 3002.00: \n" +
                "O salário é: 4000,00\n" + "O valor a ser pago de imposto é: 260,00\n"
        assertProgramOutput(input, expectedOutput)
    }

    @Test
    fun testSalario28Percent(){
        val input = "6000.00"
        val expectedOutput = "Coloque o valor do salário em R\$ seguindo o padrão 3002.00: \n" +
                "O salário é: 6000,00\n" + "O valor a ser pago de imposto é: 770,00\n"
        assertProgramOutput(input, expectedOutput)
    }

    @Test
    fun testSalarioInvalido(){
        val input = "aaa"
        val expectedOutput = "Coloque o valor do salário em R\$ seguindo o padrão 3002.00: \n" +
                "Valor de salário inválido. Certifique-se de digitar um número válido.\n"
        assertProgramOutput(input, expectedOutput)
    }

    private fun assertProgramOutput(input: String, expectedOutput: String) {
        val inputStreamn= ByteArrayInputStream(input.toByteArray())
        val outputStream = ByteArrayOutputStream()
        System.setIn(inputStreamn)
        System.setOut(PrintStream(outputStream))

        main((arrayOf()))

        assertEquals(expectedOutput, outputStream.toString())

    }
}