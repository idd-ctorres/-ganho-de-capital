package file

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.InputStream
import java.io.OutputStream

/*
    Estratégia para ler um InputStream com conteúdo Yaml
 */
class StreamReadWriteYamlStrategy: StreamReadWriteStrategy {
    override fun handle(type: String): Boolean {
        return "yaml" == type
    }

    override fun <T> read(resource: InputStream, klass: Class<T>): T {

        return ObjectMapper(YAMLFactory()).registerKotlinModule().readValue(resource, klass)
    }

    override fun <T> write(outputStream: OutputStream, value: T, klass: Class<T>) {

        throw RuntimeException("Not yet implemented")
    }
}