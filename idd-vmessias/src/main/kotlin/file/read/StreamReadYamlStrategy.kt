package file.read

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.InputStream

/*
    Estratégia para ler um InputStream com conteúdo Yaml
 */
class StreamReadYamlStrategy: StreamReadStrategy {
    override fun handle(type: String): Boolean {
        return "yaml" == type || "yml" == type
    }

    override fun <T> read(resource: InputStream, klass: Class<T>): T {

        return ObjectMapper(YAMLFactory()).registerKotlinModule().readValue(resource, klass)
    }
}