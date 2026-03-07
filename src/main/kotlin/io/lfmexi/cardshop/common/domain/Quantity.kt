package io.lfmexi.cardshop.common.domain

@JvmInline
value class Quantity(val value: Long) : Comparable<Quantity> {
    override operator fun compareTo(other: Quantity): Int = value.compareTo(other.value)

    operator fun plus(other: Quantity): Quantity = Quantity(value + other.value)

    operator fun minus(other: Quantity): Quantity = Quantity(value - other.value)

    fun negate(): Quantity = Quantity(-value)

    companion object {
        val ZERO = Quantity(0L)
    }
}