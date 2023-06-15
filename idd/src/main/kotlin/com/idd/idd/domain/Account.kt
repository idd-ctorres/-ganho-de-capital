package com.idd.idd.domain

import jakarta.persistence.*
import org.apache.commons.lang3.RandomStringUtils

@Entity
@Table(name = "accounts")
data class Account (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column
    val balance: Long = 0L,

    @Column(nullable = false, unique = true)
    var number: String? = null,

    @Column(nullable = false)
    val branch: String = "3018-X",

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "holder_id")
    val holder: Holder

){
    @PrePersist
    fun generateNumber() {
        val _number: String = RandomStringUtils.random(9, "0123456789")
        this.number = "${_number.substring(0, 2)}." +
                      "${_number.substring(2, 5)}." +
                      "${_number.substring(5, 8)}" +
                      "-${_number.last()}"
    }

    fun closeIt() {
        this.isActive = false
    }
}