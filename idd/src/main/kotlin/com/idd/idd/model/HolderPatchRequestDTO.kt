package com.idd.idd.model

import com.fasterxml.jackson.annotation.JsonProperty

class HolderPatchRequestDTO (
    @JsonProperty("first_name")
    val firstName: String?,
    @JsonProperty("last_name")
    val lastName: String?,
    val cpf: String?
)