fun main(args: Array<String>) {
    try {
        println("Coloque o valor do salário em R$ seguindo o padrão 3002.00: ")
        val input = readLine()
        val salario = input?.toFloat()

        if (salario != null) {
            println("O salário é: %.2f".format(salario))

            if (salario <= 2000.00) {
                println("Isento de taxas.")
            } else if (salario <= 3000.00) {
                val imposto = (salario - 2000.00f) * 0.08f
                println("O valor a ser pago de imposto é: %.2f".format(imposto))
            } else if (salario <= 4500.00) {
                val imposto = (salario - 3000.00f) * 0.18f + 1000.00f * 0.08f
                println("O valor a ser pago de imposto é: %.2f".format(imposto))
            } else if (salario > 4500) {
                val imposto = (salario - 4500.00f) * 0.28f + 1500.00f * 0.18f + 1000.00f * 0.08f
                println("O valor a ser pago de imposto é: %.2f".format(imposto))
            }

        }
    } catch (e: NumberFormatException) {
        println("Valor de salário inválido. Certifique-se de digitar um número válido.")
    }
}