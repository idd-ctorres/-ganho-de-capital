package file

import org.junit.jupiter.api.assertThrows
import java.lang.RuntimeException
import kotlin.test.Test

class StreamReadWriteStrategyFactoryTest {

    @Test
    fun ofFileNameShouldReturnJsonStrategy() {

        val strategy = StreamReadWriteStrategyFactory.ofFileName("test.json")

        assert(strategy.javaClass.isInstance(StreamReadWriteJsonStrategy()))
    }

    @Test
    fun ofTypeShouldReturnJsonStrategy() {

        val strategy = StreamReadWriteStrategyFactory.ofType("json")

        assert(strategy.javaClass.isInstance(StreamReadWriteJsonStrategy()))
    }

    @Test
    fun ofFileNameShouldReturnYamlStrategy() {

        val strategy = StreamReadWriteStrategyFactory.ofFileName("test.yaml")

        assert(strategy.javaClass.isInstance(StreamReadWriteYamlStrategy()))
    }

    @Test
    fun ofTypeShouldReturnYamlStrategy() {

        val strategy = StreamReadWriteStrategyFactory.ofType("yaml")

        assert(strategy.javaClass.isInstance(StreamReadWriteYamlStrategy()))
    }

    @Test
    fun ofTypeShouldThrowExceptionOnStrategyNotFoundForInformedType() {

        assertThrows<RuntimeException> { StreamReadWriteStrategyFactory.ofType("abcde") }

    }
}