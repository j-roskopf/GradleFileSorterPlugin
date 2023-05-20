package com.joetr.gradlefileformatter

import com.intellij.openapi.ui.Messages

object Messages {
    fun showMessage(message: String) {
        Messages.showInfoMessage(message, "Gradle File Formatter")
    }
}
