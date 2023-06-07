package com.idd.idd.model

import com.fasterxml.jackson.annotation.JsonProperty

class HolderResponseDTO(
        @JsonProperty("holder_external_id")
        val externalId: String,
        @JsonProperty("first_name")
        val firstName: String,
        @JsonProperty("last_name")
        val lastName: String
)