package com.shihab.multipaymentsystem.models

import java.util.UUID

enum class PaymentType { CASH, CARD, MFS }

data class PaymentRecord(
    val id: String = UUID.randomUUID().toString(),
    val type: PaymentType,
    val amount: Double,
    val provider: String? = null,
    val reference: String? = null
)