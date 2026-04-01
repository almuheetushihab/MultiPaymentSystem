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
    var inputProvider by mutableStateOf("")  // Visa, bKash etc.

    // টোটাল কত টাকা পেমেন্ট করা হলো (লিস্টের সব পেমেন্টের যোগফল)
    val totalPaid by derivedStateOf {
        paymentsList.sumOf { it.amount }
    }

    // ডিউ এবং রাউন্ডিং লজিক
    val remainingDue by derivedStateOf {
        val due = grandTotal - totalPaid
        (due * 100).roundToInt() / 100.0
    }

    // পেমেন্ট লিস্টে অ্যাড করার ফাংশন
    fun addPayment() {
        val amount = inputAmount.toDoubleOrNull() ?: return

        // Validation: এমটি রেফারেন্স চেক
        if (selectedMethod != PaymentType.CASH && inputReference.isBlank()) return

        val record = PaymentRecord(
            type = selectedMethod,
            amount = amount,
            provider = if (selectedMethod == PaymentType.CASH) "Cash" else inputProvider,
            reference = if (selectedMethod == PaymentType.CASH) null else inputReference
        )

        paymentsList.add(record)

        // অ্যাড হওয়ার পর ইনপুট ফিল্ড ক্লিয়ার করে দেওয়া
        inputAmount = ""
        inputReference = ""
        inputProvider = ""
    }

    // ভুল করে অ্যাড করলে রিমুভ করার অপশন
    fun removePayment(record: PaymentRecord) {
        paymentsList.remove(record)
    }

    // অর্ডার কমপ্লিট লজিক
    val isOrderCompleteEnabled by derivedStateOf {
        remainingDue <= 0.0
    }
}