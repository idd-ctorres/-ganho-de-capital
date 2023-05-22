package com.idd.idd.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {
    @GetMapping("/health")
    fun healthCheck(): String {
        return "Application is running!"
    }
}
