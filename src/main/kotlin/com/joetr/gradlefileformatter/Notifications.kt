package com.joetr.gradlefileformatter

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.ProjectManager

object Notifications {
    fun showCompleteNotification() {
        val notification = Notification(
            "GradleFileSorter",
            "Complete",
            "Sorting has been completed",
            NotificationType.INFORMATION
        )

        notification.notify(ProjectManager.getInstance().openProjects[0])
    }
}
