package config

import file.StreamReadWriteStrategyFactory
import file.read

class Config private constructor(val tax: TaxConfig) {

    companion object {
        private var instance: Config? = null;
        private const val fileName = "config.yaml"

        fun getInstance(): Config {

            if (instance == null) {
                instance = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName)!!.let {
                    StreamReadWriteStrategyFactory.ofFileName(fileName).read<Config>(it)
                }
            }

            return instance!!
        }
    }

}