import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.Test
import kotlin.test.assertEquals

internal class MainTest {

    private val stdOut = ByteArrayOutputStream()

    @BeforeEach
    fun setUp() {
        System.setOut(PrintStream(stdOut))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(System.out)
    }

    private fun escreverStdIn(input: String) {
        System.setIn(ByteArrayInputStream(input.toByteArray()))
    }

    @Test
    fun deveCalcularTaxasComUmaOperacaoDeCompraEDuasDeVenda() {
        escreverStdIn("[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 100}, {\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 50}, {\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 50}]\n")
        main()
        assertEquals("[{\"tax\":0.00},{\"tax\":0.00},{\"tax\":0.00}]\n", stdOut.toString())
    }

    @Test
    fun deveCalcularTaxasComUmaOperacaoDeCompraEDuasDeVendaComPagamentoDeTaxas() {
        escreverStdIn("[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000}, {\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 5000}, {\"operation\":\"sell\", \"unit-cost\":5.00, \"quantity\": 5000}]\n")
        main()
        assertEquals("[{\"tax\":0.00},{\"tax\":10000.00},{\"tax\":0.00}]\n", stdOut.toString())
    }

    @Test
    fun deveCalcularDuasLinhasDeOperacaoSeparadas() {
        escreverStdIn(
            "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 100}, {\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 50}, {\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 50}]\n"
                    + "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000}, {\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 5000}, {\"operation\":\"sell\", \"unit-cost\":5.00, \"quantity\": 5000}]\n"
        )
        main()
        assertEquals(
            ("[{\"tax\":0.00},{\"tax\":0.00},{\"tax\":0.00}]\n"
                    + "[{\"tax\":0.00},{\"tax\":10000.00},{\"tax\":0.00}]\n"), stdOut.toString()
        )
    }

    @Test
    fun deveCalcularTaxasComUmaOperacaoDeCompraEDuasDeVendaComTaxaDeMil() {
        escreverStdIn("[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000}, {\"operation\":\"sell\", \"unit-cost\":5.00, \"quantity\": 5000}, {\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 3000}]\n")
        main()
        assertEquals("[{\"tax\":0.00},{\"tax\":0.00},{\"tax\":1000.00}]\n", stdOut.toString())
    }

    @Test
    fun deveCalcularTaxasComDuasOperacoesDeCompraEUmaDeVenda() {
        escreverStdIn("[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000}, {\"operation\":\"buy\", \"unit-cost\":25.00, \"quantity\": 5000}, {\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 10000}]\n")
        main()
        assertEquals("[{\"tax\":0.00},{\"tax\":0.00},{\"tax\":0.00}]\n", stdOut.toString())
    }

    @Test
    fun deveCalcularTaxasComDuasOperacoesDeCompraEDuasDeVenda() {
        escreverStdIn("[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000}, {\"operation\":\"buy\", \"unit-cost\":25.00, \"quantity\": 5000}, {\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 10000}, {\"operation\":\"sell\", \"unit-cost\":25.00, \"quantity\": 5000}]\n")
        main()
        assertEquals("[{\"tax\":0.00},{\"tax\":0.00},{\"tax\":0.00},{\"tax\":10000.00}]\n", stdOut.toString())
    }

    @Test
    fun deveCalcularTaxasComUmaOperacaoDeCompraEQuatroDeVenda() {
        escreverStdIn("[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000}, {\"operation\":\"sell\", \"unit-cost\":2.00, \"quantity\": 5000}, {\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 2000}, {\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 2000}, {\"operation\":\"sell\", \"unit-cost\":25.00, \"quantity\": 1000}]\n")
        main()
        assertEquals(
            "[{\"tax\":0.00},{\"tax\":0.00},{\"tax\":0.00},{\"tax\":0.00},{\"tax\":3000.00}]\n",
            stdOut.toString()
        )
    }

    @Test
    fun deveCalcularTaxasComOperacoesIntercaladasDeCompraEVenda() {
        escreverStdIn("[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000}, {\"operation\":\"sell\", \"unit-cost\":2.00, \"quantity\": 5000}, {\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 2000}, {\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 2000}, {\"operation\":\"sell\", \"unit-cost\":25.00, \"quantity\": 1000}, {\"operation\":\"buy\", \"unit-cost\":20.00, \"quantity\": 10000}, {\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 5000}, {\"operation\":\"sell\", \"unit-cost\":30.00, \"quantity\": 4350}, {\"operation\":\"sell\", \"unit-cost\":30.00, \"quantity\": 650}]\n")
        main()
        assertEquals(
            "[{\"tax\":0.00},{\"tax\":0.00},{\"tax\":0.00},{\"tax\":0.00},{\"tax\":3000.00},{\"tax\":0.00},{\"tax\":0.00},{\"tax\":3700.00},{\"tax\":0.00}]\n",
            stdOut.toString()
        )
    }

    @Test
    fun deveCalcularTaxasComQuatroOperacoesIntercaladasDeCompraEVenda() {
        escreverStdIn("[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000}, {\"operation\":\"sell\", \"unit-cost\":50.00, \"quantity\": 10000}, {\"operation\":\"buy\", \"unit-cost\":20.00, \"quantity\": 10000}, {\"operation\":\"sell\", \"unit-cost\":50.00, \"quantity\": 10000}]\n")
        main()
        assertEquals(
            "[{\"tax\":0.00},{\"tax\":80000.00},{\"tax\":0.00},{\"tax\":60000.00}]\n",
            stdOut.toString()
        )
    }

}