package util

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat

object NumberUtil {

    fun round(value: BigDecimal) : BigDecimal {
        return value.setScale(2, RoundingMode.HALF_EVEN)
    }

    fun round(scale: Int? = null, block: () -> BigDecimal) : BigDecimal {

        val newScale = scale ?: 2

        return block.invoke().setScale(newScale, RoundingMode.HALF_EVEN)
    }

}