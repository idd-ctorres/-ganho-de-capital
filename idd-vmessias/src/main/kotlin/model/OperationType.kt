package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class OperationType {

    @SerialName("buy")
    BUY,

    @SerialName("sell")
    SELL
}