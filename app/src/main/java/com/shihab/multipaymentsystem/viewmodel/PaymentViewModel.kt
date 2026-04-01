package com.shihab.multipaymentsystem.viewmodel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.math.roundToInt

class PaymentViewModel : ViewModel() {

    val grandTotal = 7636.36

    var cashInput by mutableStateOf("")
    var cardInput by mutableStateOf("")
    var mfsInput by mutableStateOf("")

    val isCashError by derivedStateOf {
        cashInput.contains(".") || cashInput.contains(",")
    }

    private val cashAmount by derivedStateOf { if (isCashError) 0.0 else cashInput.toDoubleOrNull() ?: 0.0 }
    private val cardAmount by derivedStateOf { cardInput.toDoubleOrNull() ?: 0.0 }
    private val mfsAmount by derivedStateOf { mfsInput.toDoubleOrNull() ?: 0.0 }

    val totalPaid by derivedStateOf {
        cashAmount + cardAmount + mfsAmount
    }

    val remainingDue by derivedStateOf {
        val due = grandTotal - totalPaid
        (due * 100).roundToInt() / 100.0
    }

    val changeAmount by derivedStateOf {
        if (remainingDue < 0) -remainingDue else 0.0
    }

    val isOrderCompleteEnabled by derivedStateOf {
        !isCashError && remainingDue <= 0.0
    }
}