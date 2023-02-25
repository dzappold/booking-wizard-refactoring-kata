package com.airdream.booking

import com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErrAndOutNormalized
import com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn
import org.approvaltests.Approvals.verify
import org.junit.jupiter.api.Test

class BookingWizardTest {

    @Test
    fun `outward flight with 2 passengers`() {
        val output = tapSystemErrAndOutNormalized {
            withTextFromSystemIn(
                "1",
                "Frankfurt",
                "London",
                "10/12/2024",
                "2",
                "Michael Collins",
                "Sue Morrison",
                "Yes"
            ).execute {
                val wizard = BookingWizard()
                wizard.start()
            }
        }
        verify(output)
    }

    @Test
    fun `round trip flight with 4 passengers`() {
        val output = tapSystemErrAndOutNormalized {
            withTextFromSystemIn(
                "2",
                "Frankfurt",
                "London",
                "26/07/2024",
                "26/09/2024",
                "4",
                "James Hetfield",
                "Marie",
                "Michael Collins",
                "Sue Morrison",
                "Yes"
            ).execute {
                val wizard = BookingWizard()
                wizard.start()
            }
        }
        verify(output)
    }

    @Test
    fun `round trip flight with 4 passengers no confirmation`() {
        val output = tapSystemErrAndOutNormalized {
            withTextFromSystemIn(
                "2",
                "Frankfurt",
                "London",
                "26/07/2024",
                "26/09/2024",
                "4",
                "James Hetfield",
                "Marie",
                "Michael Collins",
                "Sue Morrison",
                "No"
            ).execute {
                val wizard = BookingWizard()
                wizard.start()
            }
        }
        verify(output)
    }

    @Test
    fun `invalid trip`() {
        val output = tapSystemErrAndOutNormalized {
            withTextFromSystemIn(
                "7",
                "abc",
                "2",
                "Frankfurt",
                "London",
                "26/07/2024",
                "26/09/2024",
                "4",
                "James Hetfield",
                "Marie",
                "Michael Collins",
                "Sue Morrison",
                "No"
            ).execute {
                val wizard = BookingWizard()
                wizard.start()
            }
        }
        verify(output)
    }

    @Test
    fun `departure in the past`() {
        val output = tapSystemErrAndOutNormalized {
            withTextFromSystemIn(
                "7",
                "abc",
                "2",
                "Frankfurt",
                "London",
                "26/07/1999",
                "26/07/2024",
                "26/09/2024",
                "4",
                "James Hetfield",
                "Marie",
                "Michael Collins",
                "Sue Morrison",
                "No"
            ).execute {
                val wizard = BookingWizard()
                wizard.start()
            }
        }
        verify(output)
    }

    @Test
    fun `invalid departure date`() {
        val output = tapSystemErrAndOutNormalized {
            withTextFromSystemIn(
                "7",
                "abc",
                "2",
                "Frankfurt",
                "London",
                "26#07--1999",
                "26/07/2024",
                "26/09/2024",
                "4",
                "James Hetfield",
                "Marie",
                "Michael Collins",
                "Sue Morrison",
                "No"
            ).execute {
                val wizard = BookingWizard()
                wizard.start()
            }
        }
        verify(output)
    }
    @Test
    fun `return date in the past`() {
        val output = tapSystemErrAndOutNormalized {
            withTextFromSystemIn(
                "2",
                "Frankfurt",
                "London",
                "26/07/2024",
                "26/07/1999",
                "26/09/2024",
                "1",
                "Sue Morrison",
                "Yes"
            ).execute {
                val wizard = BookingWizard()
                wizard.start()
            }
        }
        verify(output)
    }

    @Test
    fun `return date before departure`() {
        val output = tapSystemErrAndOutNormalized {
            withTextFromSystemIn(
                "2",
                "Frankfurt",
                "London",
                "26/07/2024",
                "26/07/2023",
                "26/09/2024",
                "1",
                "Sue Morrison",
                "Yes"
            ).execute {
                val wizard = BookingWizard()
                wizard.start()
            }
        }
        verify(output)
    }
    @Test
    fun `return date invalid format`() {
        val output = tapSystemErrAndOutNormalized {
            withTextFromSystemIn(
                "2",
                "Frankfurt",
                "London",
                "26/07/2024",
                "26#07--2023",
                "26/09/2024",
                "1",
                "Sue Morrison",
                "Yes"
            ).execute {
                val wizard = BookingWizard()
                wizard.start()
            }
        }
        verify(output)
    }
    @Test
    fun `invalid passenger number`() {
        val output = tapSystemErrAndOutNormalized {
            withTextFromSystemIn(
                "2",
                "Frankfurt",
                "London",
                "26/07/2024",
                "26/09/2024",
                "abc",
                "1",
                "Sue Morrison",
                "Yes"
            ).execute {
                val wizard = BookingWizard()
                wizard.start()
            }
        }
        verify(output)
    }
    @Test
    fun `invalid confirmation`() {
        val output = tapSystemErrAndOutNormalized {
            withTextFromSystemIn(
                "2",
                "Frankfurt",
                "London",
                "26/07/2024",
                "26/09/2024",
                "1",
                "Sue Morrison",
                "something",
                "No"
            ).execute {
                val wizard = BookingWizard()
                wizard.start()
            }
        }
        verify(output)
    }
}
