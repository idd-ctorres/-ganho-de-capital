package com.idd.idd.util

class CPFValidator {
    companion object{
        fun isCpfValid(_cpf: String): Boolean {
            val cpf: String = stripNonNumeric(_cpf)
            if (cpf.length != 11) return false
            return calculateValidity(cpf)
        }
        private fun stripNonNumeric(cpf: String): String = cpf.replace(Regex("[^0-9]"), "")
        private fun calculateValidity(_cpf: String): Boolean {
            /*
            0 1 2 3 4 5 6 7 8 9 10
            A B C D E F J H I J K
            */
            val cpfNumbers: IntArray = _cpf.toCharArray().map { it.digitToInt() }.toIntArray()
            if (cpfNumbers.all { it == cpfNumbers[0] }) return false
            var sum: Int = 0
            /*
            A	B	C	D	E	F	G	H	I
            x10	x9	x8	x7	x6	x5	x4	x3	x2
            */
            for (i in 0 until 9) {
                sum += cpfNumbers[i] * (10 - i)
            }
            var rem = sum % 11
            val j: Int = if (rem == 0 || rem == 1) 0 else 11 - rem
            if (cpfNumbers[9] != j) return false
            sum = 0
            /*
            A	B	C	D	E	F	G	H	I	J
            x11	x10	x9	x8	x7	x6	x5	x4	x3	x2
            */
            for (i in 0 until 10) {
                sum += cpfNumbers[i] * (11 - i)
            }
            rem = sum % 11
            val k: Int = if (rem == 0 || rem == 1) 0 else 11 - rem
            return cpfNumbers[10] == k
        }
    }
}