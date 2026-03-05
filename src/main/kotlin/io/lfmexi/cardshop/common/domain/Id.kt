package io.lfmexi.cardshop.common.domain

import java.util.UUID

@JvmInline
value class Id(val value: String) {
    companion object {
        fun generate() = Id(UUID.randomUUID().toString())
    }
}