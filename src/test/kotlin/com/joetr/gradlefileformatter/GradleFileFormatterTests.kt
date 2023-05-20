package com.joetr.gradlefileformatter

import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class GradleFileFormatterTests {

    private val sorter = Sorter()

    @Test
    fun `qksms file formatted correctly`() {
        sorter.reorgGradleFile(
            Inputs.QKSMS_APP_GRADLE_FILE,
            onComplete = {
                assertEquals(
                    Outputs.QK_SMS_APP_GRADLE_FILE,
                    it
                )
            },
            onError = {
                fail("Should not have errors")
            }
        )
    }

    @Test
    fun `now in android file formatted correctly`() {
        sorter.reorgGradleFile(
            Inputs.NOW_IN_ANDROID_APP_GRADLE_KTS_FILE,
            onComplete = {
                assertEquals(
                    Outputs.NOW_IN_ANDROID_GRADLE_KTS_FILE,
                    it
                )
            },
            onError = {
                fail("Should not have errors")
            }
        )
    }

    @Test
    fun `tivi file formatted correctly`() {
        sorter.reorgGradleFile(
            Inputs.TIVI_APP_GRADLE_KTS_FILE,
            onComplete = {
                assertEquals(
                    Outputs.TIVI_APP_GRADLE_KTS_FILE,
                    it
                )
            },
            onError = {
                fail("Should not have errors")
            }
        )
    }
}
