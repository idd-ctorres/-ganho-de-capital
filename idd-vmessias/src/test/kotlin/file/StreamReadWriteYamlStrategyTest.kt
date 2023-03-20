package file

import config.Config
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class StreamReadWriteYamlStrategyTest {

    @Test
    fun mustReadAYamlInputStreamToConfigModel() {

        val yaml = """tax:
  percentage: 20
  minOperation: 20000.01"""

        val config = StreamReadWriteYamlStrategy().read<Config>(yaml.toByteArray().inputStream())

        assertEquals(20000.01, config.tax.minOperation)
        assertEquals(20.00, config.tax.percentage)
    }

}