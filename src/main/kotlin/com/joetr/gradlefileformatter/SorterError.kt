package com.joetr.gradlefileformatter

sealed class SorterError {
    object NoDependenciesBlock : SorterError()
}
