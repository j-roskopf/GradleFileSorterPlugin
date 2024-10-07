package com.joetr.gradlefileformatter

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager

class GradleFileFormatterToolsAction : AnAction() {

    private val sorter = Sorter()

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project: Project = event.project ?: return

        val fileNamesForErrors = mutableListOf<String>()

        ProjectRootManager.getInstance(project).contentRoots.forEach { contentRootFile ->
            if (contentRootFile.isDirectory) {
                val testList = findFilesWithExtensions(contentRootFile, mutableListOf("gradle", "kts"))
                testList.forEach { file ->
                    try {
                        sorter.reorgGradleFile(
                            text = PsiManager.getInstance(project).findFile(file)?.text.orEmpty(),
                            onComplete = {
                                WriteAction.run<Throwable> {
                                    val charset = file.charset
                                    file.getOutputStream(file).use { stream -> stream.write(it.toByteArray(charset)) }
                                    file.refresh(true, true)
                                }
                            },
                            onError = {
                                fileNamesForErrors.add(file.name)
                            }
                        )
                    } catch (exception: Exception) {
                        fileNamesForErrors.add(file.name)
                    }
                }
            }
        }

        if (fileNamesForErrors.isNotEmpty()) {
            Messages.showMessage(
                "Errors occurred on the following files: ${fileNamesForErrors.joinToString(separator = ", \n")}"
            )
        }

        Notifications.showCompleteNotification()
    }

    private fun findFilesWithExtensions(file: VirtualFile, extensions: MutableList<String>): MutableList<VirtualFile> {
        val foundFiles = mutableListOf<VirtualFile>()

        @Suppress("UnsafeVfsRecursion")
        if (file.isDirectory) {
            val files = file.children
            if (files != null) {
                for (childFile in files) {
                    foundFiles.addAll(findFilesWithExtensions(childFile, extensions))
                }
            }
        } else {
            val fileExtension = file.extension
            if (extensions.contains(fileExtension) && (file.name != "settings.gradle" && file.name != "settings.gradle.kts")) {
                foundFiles.add(file)
            }
        }

        return foundFiles
    }
}
