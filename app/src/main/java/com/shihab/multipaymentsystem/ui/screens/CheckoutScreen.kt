package com.shihab.multipaymentsystem.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shihab.multipaymentsystem.models.PaymentType
import com.shihab.multipaymentsystem.viewmodel.PaymentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(viewModel: PaymentViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E88E5))
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "Grand Total",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                    Text(
                        "৳${viewModel.grandTotal}",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        "Remaining Due",
                        color = Color(0xFFFFCDD2),
                        fontSize = 16.sp
                    )
                    Text(
                        text = "৳${if (viewModel.remainingDue > 0) viewModel.remainingDue else 0.0}",
                        color = if (viewModel.remainingDue > 0) Color.Yellow else Color.White,
                        fontSize = 24.sp, fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Add Payment",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PaymentType.entries.forEach { type ->
                FilterChip(
                    selected = viewModel.selectedMethod == type,
                    onClick = { viewModel.selectedMethod = type },
                    label = { Text(type.name) }
                )
            }
        }

        OutlinedTextField(
            value = viewModel.inputAmount,
            onValueChange = { viewModel.inputAmount = it },
            label = { Text("Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        if (viewModel.selectedMethod != PaymentType.CASH) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = viewModel.inputProvider,
                    onValueChange = { viewModel.inputProvider = it },
                    label = { Text(if (viewModel.selectedMethod == PaymentType.CARD) "Network (Visa/MC)" else "Provider (bKash)") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = viewModel.inputReference,
                    onValueChange = { viewModel.inputReference = it },
                    label = { Text(if (viewModel.selectedMethod == PaymentType.CARD) "Last 4 Digits" else "Phone Number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { viewModel.addPayment() },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add to Ledger")
        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Applied Payments",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(top = 8.dp)
        ) {
            items(viewModel.paymentsList) { record ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            val icon = when (record.type) {
                                PaymentType.CASH -> "💵 Cash"
                                PaymentType.CARD -> "💳 Card (${record.provider})"
                                PaymentType.MFS -> "📱 MFS (${record.provider})"
                            }
                            Text(icon, fontWeight = FontWeight.Bold)
                            if (record.reference != null) {
                                Text(
                                    if (record.type == PaymentType.CARD) "**** ${record.reference}" else record.reference,
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                            }
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "৳${record.amount}",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32)
                            )
                            IconButton(onClick = { viewModel.removePayment(record) }) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Remove",
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }

        Button(
            onClick = { /* Success Logic */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = viewModel.isOrderCompleteEnabled,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
        ) {
            Text(
                if (viewModel.isOrderCompleteEnabled) "Complete Order" else "Due Pending...",
                fontSize = 18.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CheckoutPreview() {
    MaterialTheme { CheckoutScreen() }
}