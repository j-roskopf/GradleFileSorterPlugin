package com.joetr.gradlefileformatter

object Constants {

    val moduleTypesKts = listOf(
        "val ",

        "coreLibraryDesugaring(project",
        "coreLibraryDesugaring(projects",
        "coreLibraryDesugaring(platform",
        "coreLibraryDesugaring(kotlin",
        "coreLibraryDesugaring(libs",
        "coreLibraryDesugaring('",
        "coreLibraryDesugaring(\"",

        "api(project",
        "api(projects",
        "api(platform",
        "api(kotlin",
        "api(libs",
        "api('",
        "api(\"",

        "compileOnly(project",
        "compileOnly(projects",
        "compileOnly(platform",
        "compileOnly(kotlin",
        "compileOnly(libs",
        "compileOnly('",
        "compileOnly(\"",

        "ksp(project",
        "ksp(projects",
        "ksp(libs",
        "ksp('",
        "ksp(\"",

        "implementation(project",
        "implementation(projects",
        "implementation(platform",
        "implementation(kotlin",
        "implementation(libs",
        "implementation('",
        "implementation(\"",

        "anvil(project",
        "anvil(projects",
        "anvil(platform",
        "anvil(kotlin",
        "anvil(libs",
        "anvil('",
        "anvil(\"",

        "kapt(project",
        "kapt(projects",
        "kapt(libs",
        "kapt('",
        "kapt(\"",

        "kaptRelease(project",
        "kaptRelease(projects",
        "kaptRelease(libs",
        "kaptRelease('",
        "kaptRelease(\"",

        "lintChecks(project",
        "lintChecks(projects",
        "lintChecks(libs",
        "lintChecks('",

        "debugImplementation(project",
        "debugImplementation(projects",
        "debugImplementation(platform",
        "debugImplementation(kotlin",
        "debugImplementation(libs",
        "debugImplementation('",
        "debugImplementation(\"",

        "debugApi(project",
        "debugApi(projects",
        "debugApi(platform",
        "debugApi(kotlin",
        "debugApi(libs",
        "debugApi('",
        "debugApi(\"",

        "qaImplementation(project",
        "qaImplementation(projects",
        "qaImplementation(platform",
        "qaImplementation(kotlin",
        "qaImplementation(libs",
        "qaImplementation('",
        "qaImplementation(\"",

        "androidTestImplementation(project",
        "androidTestImplementation(projects",
        "androidTestImplementation(platform",
        "androidTestImplementation(testFixtures",
        "androidTestImplementation(kotlin",
        "androidTestImplementation(libs",
        "androidTestImplementation('",
        "androidTestImplementation(\"",

        "testApi(project",
        "testApi(projects",
        "testApi(platform",
        "testApi(testFixtures",
        "testApi(kotlin",
        "testApi(libs",
        "testApi('",
        "testApi(\"",

        "testFixturesApi(project",
        "testFixturesApi(projects",
        "testFixturesApi(platform",
        "testFixturesApi(testFixtures",
        "testFixturesApi(kotlin",
        "testFixturesApi(libs",
        "testFixturesApi('",
        "testFixturesApi(\"",

        "testFixturesImplementation(project",
        "testFixturesImplementation(projects",
        "testFixturesImplementation(platform",
        "testFixturesImplementation(testFixtures",
        "testFixturesImplementation(kotlin",
        "testFixturesImplementation(libs",
        "testFixturesImplementation('",
        "testFixturesImplementation(\"",

        "testFixtures(project",
        "testFixtures(projects",
        "testFixtures(platform",
        "testFixtures(testFixtures",
        "testFixtures(kotlin",
        "testFixtures(libs",
        "testFixtures('",
        "testFixtures(\"",

        "testImplementation(project",
        "testImplementation(projects",
        "testImplementation(platform",
        "testImplementation(testFixtures",
        "testImplementation(kotlin",
        "testImplementation(libs",
        "testImplementation('",
        "testImplementation(\""
    )

    val moduleTypesGroovy = listOf(
        "def ",

        "coreLibraryDesugaring project",
        "coreLibraryDesugaring projects",
        "coreLibraryDesugaring platform",
        "coreLibraryDesugaring kotlin",
        "coreLibraryDesugaring libs",
        "coreLibraryDesugaring '",
        "coreLibraryDesugaring \"",

        "api project",
        "api projects",
        "api platform",
        "api kotlin",
        "api libs",
        "api '",
        "api \"",

        "compileOnly project",
        "compileOnly projects",
        "compileOnly platform",
        "compileOnly kotlin",
        "compileOnly libs",
        "compileOnly '",
        "compileOnly \"",

        "ksp project",
        "ksp projects",
        "ksp libs",
        "ksp '",
        "ksp \"",

        "implementation project",
        "implementation projects",
        "implementation platform",
        "implementation kotlin",
        "implementation libs",
        "implementation '",
        "implementation \"",

        "anvil project",
        "anvil projects",
        "anvil platform",
        "anvil kotlin",
        "anvil libs",
        "anvil '",
        "anvil \"",

        "kapt project",
        "kapt projects",
        "kapt libs",
        "kapt '",
        "kapt \"",

        "kaptRelease project",
        "kaptRelease projects",
        "kaptRelease libs",
        "kaptRelease '",
        "kaptRelease \"",

        "lintChecks project",
        "lintChecks projects",
        "lintChecks libs",
        "lintChecks '",
        "lintChecks \"",

        "debugImplementation project",
        "debugImplementation projects",
        "debugImplementation platform",
        "debugImplementation kotlin",
        "debugImplementation libs",
        "debugImplementation '",
        "debugImplementation \"",

        "debugApi project",
        "debugApi projects",
        "debugApi platform",
        "debugApi kotlin",
        "debugApi libs",
        "debugApi '",
        "debugApi \"",

        "qaImplementation project",
        "qaImplementation projects",
        "qaImplementation platform",
        "qaImplementation kotlin",
        "qaImplementation libs",
        "qaImplementation '",
        "qaImplementation \"",

        "androidTestImplementation project",
        "androidTestImplementation projects",
        "androidTestImplementation platform",
        "androidTestImplementation testFixtures",
        "androidTestImplementation kotlin",
        "androidTestImplementation libs",
        "androidTestImplementation '",
        "androidTestImplementation \"",

        "testApi project",
        "testApi projects",
        "testApi platform",
        "testApi testFixtures",
        "testApi kotlin",
        "testApi libs",
        "testApi '",
        "testApi \"",

        "testFixturesApi project",
        "testFixturesApi projects",
        "testFixturesApi platform",
        "testFixturesApi testFixtures",
        "testFixturesApi kotlin",
        "testFixturesApi libs",
        "testFixturesApi '",
        "testFixturesApi \"",

        "testFixturesImplementation project",
        "testFixturesImplementation projects",
        "testFixturesImplementation platform",
        "testFixturesImplementation testFixtures",
        "testFixturesImplementation kotlin",
        "testFixturesImplementation libs",
        "testFixturesImplementation '",
        "testFixturesImplementation \"",

        "testFixtures project",
        "testFixtures projects",
        "testFixtures platform",
        "testFixtures testFixtures",
        "testFixtures kotlin",
        "testFixtures libs",
        "testFixtures '",
        "testFixtures \"",

        "testImplementation project",
        "testImplementation projects",
        "testImplementation platform",
        "testImplementation testFixtures",
        "testImplementation kotlin",
        "testImplementation libs",
        "testImplementation '",
        "testImplementation \""
    )
}
