package io.lfmexi.cardshop.common.domain

import java.math.BigDecimal

@JvmInline
value class Amount(val value: BigDecimal): Comparable<Amount> {
    override operator fun compareTo(other: Amount) = value.compareTo(other.value)
}