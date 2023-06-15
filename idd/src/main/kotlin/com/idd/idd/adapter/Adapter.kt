package com.idd.idd.adapter

interface Adapter <T, K> {
    fun adaptTo(source: T): K
}