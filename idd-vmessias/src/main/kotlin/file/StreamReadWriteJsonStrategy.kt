package file

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import kotlinx.serialization.serializer
import java.io.InputStream
import java.io.OutputStream

/*
    Estratégia para ler um InputStream com conteúdo Json
 */
class StreamReadWriteJsonStrategy: StreamReadWriteStrategy {
    override fun handle(type: String): Boolean {
        return "json" == type
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun <T> read(resource: InputStream, klass: Class<T>): T {

        @Suppress("UNCHECKED_CAST")
        // Para utilizar em conjunto com o parâmetro do Class precisa criar um serializer antes
        val deserializer = serializer(klass) as KSerializer<T>

        return Json.decodeFromStream(deserializer, resource)
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun <T> write(outputStream: OutputStream, value: T, klass: Class<T>) {

        @Suppress("UNCHECKED_CAST")
        // Para utilizar em conjunto com o parâmetro do Class precisa criar um serializer antes
        val deserializer = serializer(klass) as KSerializer<T>

        Json.encodeToStream(deserializer, value, outputStream)
    }


}