package com.idd.idd.domain

import jakarta.persistence.*
import org.apache.commons.lang3.RandomStringUtils


@Entity
data class Holder (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var firstName: String,


    @Column(nullable = false)
    var lastName: String,

    @Column(nullable = false, unique = true)
    var cpf: String,

    @Column(nullable = false, unique = true)
    var externalId: String? = null,

    @OneToMany(mappedBy = "holder", cascade = [CascadeType.ALL], orphanRemoval = true)
    val accounts: MutableList<Account> = mutableListOf()
){
    @PrePersist
    fun generateExternalId() {
        val secondPart: String = RandomStringUtils.random(12, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ")
        this.externalId = "HOL-$secondPart"
    }
}