package model

import kotlinx.serialization.Serializable

@Serializable
data class OperationResults(
    val tax: Double
) {
}