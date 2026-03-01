package io.lfmexi.cardshop

import org.junit.jupiter.api.Test
import org.springframework.modulith.core.ApplicationModules

class ModuleVerificationTest {
    @Test
    fun moduleVerification() {
        ApplicationModules.of(CardshopApplication::class.java)
            .verify()
    }
}