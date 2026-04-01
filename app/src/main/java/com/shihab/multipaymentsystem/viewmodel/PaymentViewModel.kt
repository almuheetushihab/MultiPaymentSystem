package com.shihab.multipaymentsystem.viewmodel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.shihab.multipaymentsystem.models.PaymentRecord
import com.shihab.multipaymentsystem.models.PaymentType
import kotlin.math.roundToInt

class PaymentViewModel : ViewModel() {

    val grandTotal = 7636.36

    val paymentsList = mutableStateListOf<PaymentRecord>()

    var selectedMethod by mutableStateOf(PaymentType.CASH)
    var inputAmount by mutableStateOf("")

    var inputReference by mutableStateOf("")
    var inputProvider by mutableStateOf("")

    val totalPaid by derivedStateOf {
        paymentsList.sumOf { it.amount }
    }

    val remainingDue by derivedStateOf {
        val due = grandTotal - totalPaid
        (due * 100).roundToInt() / 100.0
    }

    fun addPayment() {
        val amount = inputAmount.toDoubleOrNull() ?: return

        if (selectedMethod != PaymentType.CASH && inputReference.isBlank()) return

        val record = PaymentRecord(
            type = selectedMethod,
            amount = amount,
            provider = if (selectedMethod == PaymentType.CASH) "Cash" else inputProvider,
            reference = if (selectedMethod == PaymentType.CASH) null else inputReference
        )

        paymentsList.add(record)

        inputAmount = ""
        inputReference = ""
        inputProvider = ""
    }

    fun removePayment(record: PaymentRecord) {
        paymentsList.remove(record)
    }

    val isOrderCompleteEnabled by derivedStateOf {
        remainingDue <= 0.0
    }
}