package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class Operation(
    val operation: OperationType,
    @SerialName("unit-cost")
    val unitCost: Double,

    val quantity: Double
) {

}