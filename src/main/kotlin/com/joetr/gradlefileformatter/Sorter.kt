package com.joetr.gradlefileformatter

import com.joetr.gradlefileformatter.Constants.moduleTypesGroovy
import com.joetr.gradlefileformatter.Constants.moduleTypesKts

class Sorter {

    fun reorgGradleFile(
        text: String,
        onComplete: (String) -> Unit,
        onError: (SorterError) -> Unit
    ) {
        var lines = text.split("\n").toMutableList()

        var moduleBlockData = getLocationOfDependenciesBlock(lines)

        if (moduleBlockData.startPosition == -1 || moduleBlockData.endPosition == -1) {
            onError(SorterError.NoDependenciesBlock)
            return
        }

        lines = flattenMultiLineDependencies(lines, moduleBlockData.startPosition, moduleBlockData.endPosition)

        moduleBlockData = getLocationOfDependenciesBlock(lines)

        lines = sortModuleBlock(lines, moduleBlockData.startPosition, moduleBlockData.endPosition)

        moduleBlockData = getLocationOfDependenciesBlock(lines)

        lines = trimExcessNewlines(lines, moduleBlockData.startPosition, moduleBlockData.endPosition)

        moduleBlockData = getLocationOfDependenciesBlock(lines)

        lines = addLogicalSpacesToModuleBlock(lines, moduleBlockData.startPosition, moduleBlockData.endPosition)

        val sortedFile = lines.joinToString("\n")

        onComplete(sortedFile)

        println("Gradle file sorted successfully!")
    }

    private fun sortModuleBlock(lines: MutableList<String>, start: Int, end: Int): MutableList<String> {
        val toReturn = mutableListOf<String>().apply {
            addAll(lines)
        }
        val moduleBlock = lines.subList(start, end + 1)

        moduleBlock.sortWith(compareModuleLines)

        toReturn.subList(start, end + 1).clear()
        toReturn.addAll(start, moduleBlock)

        return toReturn
    }

    private val compareModuleLines = object : Comparator<String> {
        override fun compare(a: String, b: String): Int {
            val typeIndexA = getTypeIndex(a)
            val typeIndexB = getTypeIndex(b)

            if (typeIndexA != typeIndexB) {
                return typeIndexA - typeIndexB
            }

            return a.compareTo(b)
        }
    }

    private fun addLogicalSpacesToModuleBlock(lines: MutableList<String>, start: Int, end: Int): MutableList<String> {
        val toReturn = mutableListOf<String>().apply {
            addAll(lines)
        }
        val moduleBlock = lines.subList(start, end + 1)

        var index = start

        var firstItemStart = dependencyBlockItemStart(moduleBlock[0]).trim()

        while (true) {
            val item = toReturn[index]
            if (item.trim() == "}") break

            val currentItemStart = dependencyBlockItemStart(item).trim()
            if (currentItemStart != firstItemStart && currentItemStart != "") {
                toReturn.add(index, "")
                firstItemStart = currentItemStart
            }

            index++
        }

        return toReturn
    }

    private fun trimExcessNewlines(lines: MutableList<String>, start: Int, end: Int): MutableList<String> {
        val toReturn = mutableListOf<String>().apply {
            addAll(lines)
        }
        val moduleBlock = lines.subList(start, end + 1)

        var i = 0
        while (i < moduleBlock.size) {
            val item = moduleBlock[i]
            if (item == "\n" || item == "" || item.trim().startsWith("//")) {
                moduleBlock.removeAt(i)
                i--
            }
            i++
        }

        toReturn.subList(start, end + 1).clear()
        toReturn.addAll(start, moduleBlock)

        return toReturn
    }

    private fun getLocationOfDependenciesBlock(lines: List<String>): DependenciesBlockLocation {
        var moduleBlockStart = -1
        var moduleBlockEnd = -1
        var previousValue: String? = null

        for (i in lines.indices) {
            val line = lines[i]

            if (line.contains("dependencies {")) {
                moduleBlockStart = i + 1
            }

            if (i - 1 >= 0) {
                previousValue = lines[i - 1]
            }

            if (moduleBlockStart != -1 && line.trim() == "}" && previousValue != null && !previousValue.contains("exclude")) {
                moduleBlockEnd = i
                break
            }
        }

        return DependenciesBlockLocation(moduleBlockStart, moduleBlockEnd)
    }

    private fun dependencyBlockItemStart(item: String): String {
        var moduleTypeToReturn = ""
        moduleTypesKts.forEach { moduleType ->
            if (item.trim().startsWith(moduleType)) {
                moduleTypeToReturn = moduleType
            }
        }

        moduleTypesGroovy.forEach { moduleType ->
            if (item.trim().startsWith(moduleType)) {
                moduleTypeToReturn = moduleType
            }
        }

        return moduleTypeToReturn
    }

    private fun flattenMultiLineDependencies(
        lines: MutableList<String>,
        startPosition: Int,
        endPosition: Int
    ): MutableList<String> {
        val moduleBlock = lines.subList(startPosition, endPosition + 1)

        val toReturn = mutableListOf<String>().apply {
            addAll(lines)
        }

        var index = startPosition
        val moduleBlockIterator = moduleBlock.iterator()
        while (moduleBlockIterator.hasNext()) {
            val next = moduleBlockIterator.next()
            if (next.trim().endsWith("{")) {
                var amountOfLinesOfMultiLineDependency = 1
                var foundEndBracket = false
                var multiLineDependency = "$next "
                while (!foundEndBracket) {
                    val currentLine = moduleBlockIterator.next().trim() + " "
                    multiLineDependency += currentLine
                    if (currentLine.trim() == "}" || currentLine.trim() == "})") {
                        foundEndBracket = true
                    }
                    amountOfLinesOfMultiLineDependency++
                }

                toReturn.subList(index, index + amountOfLinesOfMultiLineDependency).clear()
                toReturn.add(index, multiLineDependency)
            }
            index++
        }

        return toReturn
    }

    private fun getTypeIndex(line: String): Int {
        for (i in moduleTypesKts.indices) {
            if (line.contains(moduleTypesKts[i])) {
                return i
            }
        }

        for (i in moduleTypesGroovy.indices) {
            if (line.contains(moduleTypesGroovy[i])) {
                return i
            }
        }
        return Int.MAX_VALUE
    }
}
