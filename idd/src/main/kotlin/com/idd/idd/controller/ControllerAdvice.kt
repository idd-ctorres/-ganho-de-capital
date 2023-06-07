package com.idd.idd.controller

import com.idd.idd.exception.*
import com.idd.idd.model.ErrorDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerAdvice {
    @ExceptionHandler
    fun handleInvalidCPFException(ex: InvalidCPFException): ResponseEntity<ErrorDTO> {
        return ResponseEntity.badRequest().body(ErrorDTO(message = "CPF inválido"))
    }

    @ExceptionHandler
    fun handleInvalidExternalIdException(exception: InvalidExternalIdException): ResponseEntity<ErrorDTO> {
        return ResponseEntity.badRequest().body(ErrorDTO(message = "Identificador de usuário inválido"))
    }

    @ExceptionHandler
    fun handleHolderNotFoundException(exception: HolderNotFoundException): ResponseEntity<ErrorDTO> {
        return ResponseEntity.badRequest().body(ErrorDTO(message = "Usuário não encontrado"))
    }

    @ExceptionHandler
    fun handleInvalidPatchBodyException(exception: InvalidPatchBodyException): ResponseEntity<ErrorDTO> {
        return ResponseEntity.badRequest().body(ErrorDTO(message = "Campos com informação do usuário faltando"))
    }

    @ExceptionHandler
    fun handleAccountNotFoundException(exception: AccountNotFoundException): ResponseEntity<ErrorDTO> {
        return ResponseEntity.badRequest().body(ErrorDTO(message = "Conta não encontrada"))
    }

    @ExceptionHandler
    fun handleAccounClosedException(exception: AccountClosedException): ResponseEntity<ErrorDTO> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorDTO(message = "Conta encerrada"))
    }
}