package com.idd.idd.domain

import jakarta.persistence.*
import org.apache.commons.lang3.RandomStringUtils


@Entity
@Table(name = "holders")
data class Holder (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name ="first_name",nullable = false)
    var firstName: String,

    @Column(name = "last_name", nullable = false)
    var lastName: String,

    @Column(nullable = false, unique = true)
    var cpf: String,

    @Column(name = "external_id", nullable = false, unique = true)
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