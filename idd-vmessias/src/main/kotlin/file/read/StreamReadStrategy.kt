package file.read

import java.io.InputStream

/*
    Contrato para ler um InputStream qualquer e transformar em uma instância de uma determinada classe
 */
interface StreamReadStrategy {

    // Checa se a implementação lida com o tipo informado
    fun handle(type: String): Boolean

    // Realiza a leitura de um InputStream em uma instância da classe informada
    fun<T> read(resource: InputStream, klass: Class<T>): T

}

// Extensão para que quando for usar não precise informar o tipo da classe
// Ela será inferida automaticamente pelo tipo T (usando reified com inline)
inline fun <reified T> StreamReadStrategy.read(resource: InputStream) = read<T>(resource, T::class.java)