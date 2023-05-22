package com.idd.idd.model


data class AccountDTO (
    val id: Long,
    val balance: Long,
    val number: String,
    val branch: String,
    val holder: HolderRequestDTO
)