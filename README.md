# Excess Baggage Calculator

An Android application developed using **Java and XML in Android Studio** to calculate excess baggage charges based on baggage weight and ticket class.

## About the Project

The Excess Baggage Calculator helps users estimate additional baggage fees when their luggage exceeds the free baggage allowance.

The application calculates the excess baggage fee and includes an 8% service tax to display the total amount payable.

## Features

- **Baggage Weight Input** – Enter the total weight of your baggage.
- **Ticket Class Selection** – Select the applicable ticket class.
- **Excess Weight Calculation** – Automatically calculates baggage weight exceeding the free allowance.
- **Baggage Fee Calculation** – Calculates the additional charge based on the rate per kilogram.
- **Service Tax** – Includes an 8% service tax.
- **Payment Summary** – Displays a breakdown of charges and the total payable amount.
- **About Page** – Provides application and developer information.

## Calculation Formula

**1. Excess Weight**

Excess Weight = Baggage Weight − Free Baggage Allowance

**2. Excess Baggage Fee**

Excess Baggage Fee = Excess Weight × Rate per KG

**3. Service Tax**

Service Tax = Excess Baggage Fee × 8%

**4. Total Payable**

Total Payable = Excess Baggage Fee + Service Tax

## Example Calculation

| Description | Amount |
|---|---:|
| Ticket Class | Economy |
| Baggage Weight | 25 kg |
| Free Allowance | 20 kg |
| Excess Weight | 5 kg |
| Rate per KG | RM15.00 |
| Excess Baggage Fee | RM75.00 |
| Service Tax (8%) | RM6.00 |
| **Total Payable** | **RM81.00** |

## Technologies Used

- Java
- XML
- Android Studio
- Gradle

## Installation and Setup

1. Clone or download this repository.
2. Open Android Studio.
3. Select **Open** and choose the project folder.
4. Allow Gradle to sync.
5. Connect an Android device or start an emulator.
6. Click **Run** to launch the application.

## Project Structure

```text
ExcessBaggageCalculator/
│
├── app/
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/example/excessbaggagecalculator/
│           └── res/
│               ├── drawable/
│               ├── layout/
│               └── values/
│
├── gradle/
├── .gitignore
├── build.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
└── settings.gradle.kts
```

## Developer

**Developer:** Luqq-xyz

**Project:** Excess Baggage Calculator

## Repository

[View Excess Baggage Calculator on GitHub](https://github.com/Luqq-xyz/ExcessBaggageCalculator)

---

*This project was developed for educational purposes.*
