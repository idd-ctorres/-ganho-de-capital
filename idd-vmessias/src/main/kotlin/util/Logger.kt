package util

object Logger {

    fun debug(text : String?) {
        if (System.getenv("DEBUG") == "true") {
            info(text)
        }
    }

    fun debug() {
        debug(null)
    }

    fun info(text : String?) {
        if (text != null) {
            println(text)
        } else {
            println()
        }
    }

}