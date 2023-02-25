package com.airdream.booking

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.InputMismatchException
import java.util.Scanner

class BookingWizard {
    private var currentStep = 0
    private var tripType = 0
    private var departureCity: String? = null
    private var arrivalCity: String? = null
    private var departureDate: Date = Date()
    private var returnDate: Date? = null
    private var passengers: Array<String?> = arrayOfNulls(0)
    private var confirmed: Boolean? = null
    fun start() {
        currentStep = 0
        while (currentStep < 9) {
            wizardStep()
            ++currentStep
        }
    }

    private fun wizardStep() {
        val scanner = Scanner(System.`in`)
        if (currentStep == 0) {
            try {
                println("Hello, welcome to Airdream, the airline of your dreams")
                println("What kind of flight do you want to book?")
                println("[1] outward")
                println("[2] round trip")
                tripType = scanner.nextInt()
                if (tripType != 1 && tripType != 2) {
                    println("Invalid input. Please follow the instructions")
                    wizardStep()
                }
            } catch (e: InputMismatchException) {
                println("Invalid input. Please follow the instructions")
                wizardStep()
            }
        } else if (currentStep == 1) {
            println("Select your departure city")
            departureCity = scanner.nextLine()
        } else if (currentStep == 2) {
            println("Select your arrival city")
            arrivalCity = scanner.nextLine()
        } else if (currentStep == 3) {
            println("Select your departure date (DD/MM/YYYY)")
            val date = scanner.nextLine()
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
            try {
                departureDate = simpleDateFormat.parse(date)
                if (departureDate.before(Date())) {
                    println("Please select a date in the future.")
                    wizardStep()
                }
            } catch (e: ParseException) {
                println("Invalid input. Please follow the instructions")
                wizardStep()
            }
        } else if (currentStep == 4) {
            if (tripType == 2) {
                println("Select your return date (DD/MM/YYYY)")
                val date = scanner.nextLine()
                val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
                try {
                    returnDate = simpleDateFormat.parse(date)
                    if (returnDate?.before(Date()) == true) {
                        println("Please select a date in the future.")
                        wizardStep()
                    } else {
                        if (returnDate?.before(departureDate) == true) {
                            println("Please select a return date after your departure date.")
                            wizardStep()
                        }
                    }
                } catch (e: ParseException) {
                    println("Invalid input. Please follow the instructions")
                    wizardStep()
                }
            }
        } else if (currentStep == 5) {
            try {
                println("Please select the number of passengers")
                val size = scanner.nextInt()
                passengers = arrayOfNulls(size)
            } catch (e: InputMismatchException) {
                println("Invalid input. Please follow the instructions")
                wizardStep()
            }
        } else if (currentStep == 6) {
            for (i in passengers.indices) {
                println("Please enter the passenger number " + (i + 1) + " first name and last name")
                passengers[i] = scanner.nextLine()
            }
        } else if (currentStep == 7) {
            println("Your booking summary")
            println(
                "Flight 1: "
                        + "Departure at " + departureDate.toString() + " from " + departureCity + " and arrival in " + arrivalCity
            )
            if (returnDate != null) {
                println(
                    "Flight 2: "
                            + "Departure at " + returnDate.toString() + " from " + arrivalCity + " and arrival in " + departureCity
                )
            }
            println("Passengers:")
            for (i in passengers.indices) {
                println(passengers[i])
            }
            println("Do you confirm? [Yes/No]")
            val confirmation = scanner.nextLine()
            when (confirmation) {
                "Yes" -> confirmed = true
                "No" -> confirmed = false
                else -> {
                    println("Invalid input. Please follow the instructions")
                    wizardStep()
                }
            }
        } else if (currentStep == 8) {
            if (confirmed == null) {
                currentStep--
                wizardStep()
            } else {
                if (confirmed as Boolean) {
                    println("Thank you for your booking, we wish you an excellent trip to $arrivalCity")
                } else {
                    println("We are sorry we could not help you, and hope to see you soon. Good bye.")
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            BookingWizard().start()
        }
    }
}
