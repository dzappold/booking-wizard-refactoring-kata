package com.airdream.booking

import com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErrAndOutNormalized
import com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn
import org.approvaltests.Approvals.verify
import org.junit.jupiter.api.Test

class BookingWizardTest {

    @Test
    fun `outward flight with 2 passengers`() =
        validateFor(
            "1",
            "Frankfurt",
            "London",
            "10/12/2024",
            "2",
            "Michael Collins",
            "Sue Morrison",
            "Yes"
        )

    @Test
    fun `round trip flight with 4 passengers`() =
        validateFor(
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
        )

    @Test
    fun `round trip flight with 4 passengers no confirmation`() =
        validateFor(
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
        )

    @Test
    fun `invalid trip`() =
        validateFor(
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
        )

    @Test
    fun `departure in the past`() =
        validateFor(
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
        )

    @Test
    fun `invalid departure date`() =
        validateFor(
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
        )

    @Test
    fun `return date in the past`() =
        validateFor(
            "2",
            "Frankfurt",
            "London",
            "26/07/2024",
            "26/07/1999",
            "26/09/2024",
            "1",
            "Sue Morrison",
            "Yes"
        )

    @Test
    fun `return date before departure`() =
        validateFor(
            "2",
            "Frankfurt",
            "London",
            "26/07/2024",
            "26/07/2023",
            "26/09/2024",
            "1",
            "Sue Morrison",
            "Yes"
        )

    @Test
    fun `return date invalid format`() =
        validateFor(
            "2",
            "Frankfurt",
            "London",
            "26/07/2024",
            "26#07--2023",
            "26/09/2024",
            "1",
            "Sue Morrison",
            "Yes"
        )

    @Test
    fun `invalid passenger number`() =
        validateFor(
            "2",
            "Frankfurt",
            "London",
            "26/07/2024",
            "26/09/2024",
            "abc",
            "1",
            "Sue Morrison",
            "Yes"
        )

    @Test
    fun `invalid confirmation`() =
        validateFor(
            "2",
            "Frankfurt",
            "London",
            "26/07/2024",
            "26/09/2024",
            "1",
            "Sue Morrison",
            "something",
            "No"
        )
}

private fun validateFor(vararg input: String) =
    verify(
        tapSystemErrAndOutNormalized {
            withTextFromSystemIn(*input)
                .execute { BookingWizard().start() }
        }
    )
