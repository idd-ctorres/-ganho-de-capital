package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Operation(
    @SerialName("operation")
    val type: OperationType,
    @SerialName("unit-cost")
    val unitCost: Double,

    val quantity: Double
) {

}