package file.read

/*
    Factory que cria uma estratégia de leitura de acordo com o nome do arquivo \ tipo do conteúdo.
    Quando criado a partir de um nome do arquivo, o nome do arquivo deve conter a extensão. Exemplo: *.json, *.yaml;
    Dessa forma a classe consegue inferir o tipo do conteúdo utilizando a extensão
 */
object StreamReadStrategyFactory {

    // Lista de estratégias
    private val strategies : List<StreamReadStrategy> = listOf(
        StreamReadYamlStrategy(),
        StreamReadJsonStrategy()
    )

    fun ofFileName(fileName: String) : StreamReadStrategy {

        // Splita o filename por '.' separando a extensão
        val fileType: String = fileName.split(".")
            // Pega o index 1 representando a extensão ou retorna null
            .getOrNull(1)
            // Se retornar null então lançamos uma exceção
            ?: throw RuntimeException("Invalid filetype for filename $fileName")

        return ofType(fileType)
    }

    fun ofType(fileType: String): StreamReadStrategy {
        return strategies
            // Pega o primeiro registro utilizando o filtro informado
            .firstOrNull { t -> t.handle(fileType) }
            // Se for diferente de null então retorna se não lançamos uma exceção
            .takeIf { it != null } ?: throw RuntimeException("File strategy not found for $fileType")
    }

}