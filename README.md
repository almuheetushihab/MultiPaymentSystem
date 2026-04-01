# Multi-Payment System (Android)

A modern Android application built with **Jetpack Compose** that demonstrates a robust "Split Payment" or "Multi-Payment" logic commonly used in POS (Point of Sale) systems.

## 🚀 Overview
In real-world retail scenarios, customers often want to pay using multiple methods (e.g., some in Cash, some via Card, and the rest via bKash/MFS). This project provides a clean, reactive UI to handle such complex payment flows seamlessly.

## ✨ Key Features
- **Split Payment Support**: Allows adding multiple payment records (Cash, Card, MFS) for a single order.
- **Real-time Calculations**: Automatically calculates "Total Paid" and "Remaining Due" as payments are added or removed.
- **Dynamic Input Fields**: Input fields change dynamically based on the selected payment method (e.g., Card needs Last 4 Digits, MFS needs Provider Name and Reference).
- **Payment Ledger**: A list view showing all applied payments with the ability to remove any specific record.
- **Smart Validation**: The "Complete Order" button remains disabled until the full "Grand Total" is covered.

## 🛠 Tech Stack & Architecture
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Design System**: Material Design 3 (M3)
- **Architecture**: MVVM (Model-View-ViewModel)
- **State Management**: Compose State (`mutableStateOf`, `mutableStateListOf`, `derivedStateOf`)
- **Components**: ViewModel, Card, LazyColumn, FilterChip, OutlinedTextField.

## 📂 Project Structure
- **Models**: Defines `PaymentType` (Enum) and `PaymentRecord` (Data Class).
- **ViewModel**: `PaymentViewModel` handles all the business logic, state calculations, and ledger management.
- **UI (Screens)**: `CheckoutScreen` contains the composable UI elements, providing a clean and responsive user experience.

## 🛠 How to Use
1. Enter the amount to be paid.
2. Select the Payment Method (Cash, Card, or MFS).
3. Provide additional details if required (Provider/Reference).
4. Click **"Add to Ledger"**.
5. Once the "Remaining Due" reaches 0, the **"Complete Order"** button becomes active.

---
*Developed as a practice project to master State Management and UI logic in Jetpack Compose.*