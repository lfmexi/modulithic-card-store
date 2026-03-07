package io.lfmexi.cardshop.common.domain

import java.math.BigDecimal

@JvmInline
value class Price(
    val value: BigDecimal
): Comparable<Price> {
    override fun compareTo(other: Price) = value.compareTo(other.value)
}