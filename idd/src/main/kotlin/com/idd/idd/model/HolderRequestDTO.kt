package com.idd.idd.model

import com.fasterxml.jackson.annotation.JsonProperty


data class HolderRequestDTO(
    @JsonProperty("first_name")
    val firstName: String,
    @JsonProperty("last_name")
    val lastName: String,
    val cpf: String
)