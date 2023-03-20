package file

import java.io.InputStream
import java.io.OutputStream

/*
    Contrato para ler um InputStream qualquer e transformar em uma instância de uma determinada classe
 */
interface StreamReadWriteStrategy {

    // Checa se a implementação lida com o tipo informado
    fun handle(type: String): Boolean

    // Realiza a leitura de um InputStream em uma instância da classe informada
    fun<T> read(resource: InputStream, klass: Class<T>): T

    fun<T> write(outputStream: OutputStream, value: T, klass: Class<T>)

}

inline fun <reified T> StreamReadWriteStrategy.read(resource: InputStream) = read(resource, T::class.java)

inline fun <reified T> StreamReadWriteStrategy.write(outputStream: OutputStream, value: T) = write(outputStream, value, T::class.java)