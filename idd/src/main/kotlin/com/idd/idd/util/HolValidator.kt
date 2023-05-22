package com.idd.idd.util

class HolValidator {
    companion object{
        fun isHOLValid(external_id: String): Boolean {
            val regex: Regex = "^HOL-[A-Z0-9]{12}$".toRegex()
            return regex.matches(external_id)
        }
    }
}