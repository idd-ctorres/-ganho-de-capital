package config

import file.read.StreamReadStrategyFactory
import file.read.read

/*
    Classe que representa a configuração do sistema.

    Utilizado padrão singleton para garantir uma única instância. Durante sua criação, o arquivo yaml de configuração
    que fica contido na pasta resource é carregado automaticamente dentro dessa classe.
 */
class Config private constructor(val tax: TaxConfig) {

    companion object {
        private var instance: Config? = null;
        private const val fileName = "config.yaml"

        fun getInstance(): Config {

            if (instance == null) {
                instance = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName)!!.let {
                    // Utiliza nosso factory de stream para pegar a estratégia correta para o arquivo .yaml
                    StreamReadStrategyFactory.ofFileName(fileName).read<Config>(it)
                }
            }

            return instance!!
        }
    }

}