package com.joetr.gradlefileformatter

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.annotations.NotNull

class GradleFileFormatterPopupMenuAction : AnAction() {

    private val sorter = Sorter()

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    override fun update(@NotNull event: AnActionEvent) {
        val virtualFile: VirtualFile? = event.getData(PlatformDataKeys.VIRTUAL_FILE)

        val isSettingsGradleFile =
            virtualFile?.name?.endsWith("settings.gradle.kts") == true || virtualFile?.name?.endsWith("settings.gradle") == true
        val isBuildFile =
            virtualFile?.name?.endsWith(".gradle.kts") == true || virtualFile?.name?.endsWith(".gradle") == true

        event.presentation.isEnabledAndVisible = (
            isBuildFile && !isSettingsGradleFile
            )
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project: Project = event.getRequiredData(CommonDataKeys.PROJECT)

        val editor: Editor? = event.getData(CommonDataKeys.EDITOR)
        val document = editor?.document ?: return
        val text = editor.document.text

        try {
            sorter.reorgGradleFile(
                text = text,
                onComplete = {
                    WriteCommandAction.runWriteCommandAction(
                        project
                    ) {
                        document.setText(it)
                    }
                },
                onError = {
                    val message = when (it) {
                        is SorterError.NoDependenciesBlock -> "No dependencies block found"
                    }
                    Messages.showMessage(message)
                }
            )

            Notifications.showCompleteNotification()
        } catch (exception: Exception) {
            Messages.showMessage("An unexpected error occurred")
        }
    }
}
