package com.joetr.gradlefileformatter

object Outputs {

    val NOW_IN_ANDROID_GRADLE_KTS_FILE = """
        /*
         * Copyright 2022 The Android Open Source Project
         *
         * Licensed under the Apache License, Version 2.0 (the "License");
         * you may not use this file except in compliance with the License.
         * You may obtain a copy of the License at
         *
         *     https://www.apache.org/licenses/LICENSE-2.0
         *
         * Unless required by applicable law or agreed to in writing, software
         * distributed under the License is distributed on an "AS IS" BASIS,
         * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
         * See the License for the specific language governing permissions and
         * limitations under the License.
         */
        import com.google.samples.apps.nowinandroid.NiaBuildType
        
        plugins {
            id("nowinandroid.android.application")
            id("nowinandroid.android.application.compose")
            id("nowinandroid.android.application.flavors")
            id("nowinandroid.android.application.jacoco")
            id("nowinandroid.android.hilt")
            id("jacoco")
            id("nowinandroid.android.application.firebase")
            id("com.google.android.gms.oss-licenses-plugin")
        }
        
        android {
            defaultConfig {
                applicationId = "com.google.samples.apps.nowinandroid"
                versionCode = 5
                versionName = "0.0.5" // X.Y.Z; X = Major, Y = minor, Z = Patch level
        
                // Custom test runner to set up Hilt dependency graph
                testInstrumentationRunner = "com.google.samples.apps.nowinandroid.core.testing.NiaTestRunner"
                vectorDrawables {
                    useSupportLibrary = true
                }
            }
        
            buildTypes {
                debug {
                    applicationIdSuffix = NiaBuildType.DEBUG.applicationIdSuffix
                }
                val release by getting {
                    isMinifyEnabled = true
                    applicationIdSuffix = NiaBuildType.RELEASE.applicationIdSuffix
                    proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        
                    // To publish on the Play store a private signing key is required, but to allow anyone
                    // who clones the code to sign and run the release variant, use the debug signing key.
                    // TODO: Abstract the signing configuration to a separate file to avoid hardcoding this.
                    signingConfig = signingConfigs.getByName("debug")
                }
                create("benchmark") {
                    // Enable all the optimizations from release build through initWith(release).
                    initWith(release)
                    matchingFallbacks.add("release")
                    // Debug key signing is available to everyone.
                    signingConfig = signingConfigs.getByName("debug")
                    // Only use benchmark proguard rules
                    proguardFiles("benchmark-rules.pro")
                    isMinifyEnabled = true
                    applicationIdSuffix = NiaBuildType.BENCHMARK.applicationIdSuffix
                }
            }
        
            packaging {
                resources {
                    excludes.add("/META-INF/{AL2.0,LGPL2.1}")
                }
            }
            testOptions {
                unitTests {
                    isIncludeAndroidResources = true
                }
            }
            namespace = "com.google.samples.apps.nowinandroid"
        }
        
        dependencies {
            implementation(project(":core:analytics"))
            implementation(project(":core:common"))
            implementation(project(":core:data"))
            implementation(project(":core:designsystem"))
            implementation(project(":core:model"))
            implementation(project(":core:ui"))
            implementation(project(":feature:bookmarks"))
            implementation(project(":feature:foryou"))
            implementation(project(":feature:interests"))
            implementation(project(":feature:search"))
            implementation(project(":feature:settings"))
            implementation(project(":feature:topic"))
            implementation(project(":sync:work"))
        
            implementation(libs.accompanist.systemuicontroller)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.compose.material3.windowSizeClass)
            implementation(libs.androidx.compose.runtime)
            implementation(libs.androidx.compose.runtime.tracing)
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.core.splashscreen)
            implementation(libs.androidx.hilt.navigation.compose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.androidx.profileinstaller)
            implementation(libs.androidx.window.manager)
            implementation(libs.coil.kt)
        
            debugImplementation(project(":ui-test-hilt-manifest"))
        
            debugImplementation(libs.androidx.compose.ui.testManifest)
        
            androidTestImplementation(project(":core:data-test"))
            androidTestImplementation(project(":core:datastore-test"))
            androidTestImplementation(project(":core:network"))
            androidTestImplementation(project(":core:testing"))
        
            androidTestImplementation(kotlin("test"))
        
            androidTestImplementation(libs.accompanist.testharness)
            androidTestImplementation(libs.androidx.navigation.testing)
        }
        
        // androidx.test is forcing JUnit, 4.12. This forces it to use 4.13
        configurations.configureEach {
            resolutionStrategy {
                force(libs.junit4)
                // Temporary workaround for https://issuetracker.google.com/174733673
                force("org.objenesis:objenesis:2.6")
            }
        }
    """.trimIndent()

    val TIVI_APP_GRADLE_KTS_FILE = """
        /*
         * Copyright 2017 Google LLC
         *
         * Licensed under the Apache License, Version 2.0 (the "License");
         * you may not use this file except in compliance with the License.
         * You may obtain a copy of the License at
         *
         *     http://www.apache.org/licenses/LICENSE-2.0
         *
         * Unless required by applicable law or agreed to in writing, software
         * distributed under the License is distributed on an "AS IS" BASIS,
         * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
         * See the License for the specific language governing permissions and
         * limitations under the License.
         */


        import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

        plugins {
            id("app.tivi.android.application")
            alias(libs.plugins.kotlin.android)
            alias(libs.plugins.ksp)
        }

        val appVersionCode = propOrDef("TIVI_VERSIONCODE", "1000").toInt()
        println("APK version code: ${'$'}appVersionCode")

        val useReleaseKeystore = rootProject.file("release/app-release.jks").exists()

        android {
            namespace = "app.tivi"

            defaultConfig {
                applicationId = "app.tivi"
                versionCode = appVersionCode
                versionName = "0.9.2"

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            signingConfigs {
                getByName("debug") {
                    storeFile = rootProject.file("release/app-debug.jks")
                    storePassword = "android"
                    keyAlias = "androiddebugkey"
                    keyPassword = "android"
                }

                create("release") {
                    if (useReleaseKeystore) {
                        storeFile = rootProject.file("release/app-release.jks")
                        storePassword = propOrDef("TIVI_RELEASE_KEYSTORE_PWD", "")
                        keyAlias = "tivi"
                        keyPassword = propOrDef("TIVI_RELEASE_KEY_PWD", "")
                    }
                }
            }

            lint {
                baseline = file("lint-baseline.xml")
                // Disable lintVital. Not needed since lint is run on CI
                checkReleaseBuilds = false
                // Ignore any tests
                ignoreTestSources = true
                // Make the build fail on any lint errors
                abortOnError = true
            }

            buildFeatures {
                buildConfig = true
                compose = true
            }

            composeOptions {
                kotlinCompilerExtensionVersion = libs.versions.composecompiler.get()
            }

            testOptions {
                unitTests {
                    isIncludeAndroidResources = true
                }
            }

            packaging {
                resources.excludes += setOf(
                    // Exclude AndroidX version files
                    "META-INF/*.version",
                    // Exclude consumer proguard files
                    "META-INF/proguard/*",
                    // Exclude the Firebase/Fabric/other random properties files
                    "/*.properties",
                    "fabric/*.properties",
                    "META-INF/*.properties",
                )
            }

            buildTypes {
                debug {
                    signingConfig = signingConfigs["debug"]
                    versionNameSuffix = "-dev"
                    applicationIdSuffix = ".debug"

                    buildConfigField("String", "TRAKT_CLIENT_ID", "\"" + propOrDef("TIVI_DEBUG_TRAKT_CLIENT_ID", "TIVI_TRAKT_CLIENT_ID", "") + "\"")
                    buildConfigField("String", "TRAKT_CLIENT_SECRET", "\"" + propOrDef("TIVI_DEBUG_TRAKT_CLIENT_SECRET", "TIVI_TRAKT_CLIENT_SECRET", "") + "\"")
                    buildConfigField("String", "TMDB_API_KEY", "\"" + propOrDef("TIVI_DEBUG_TMDB_API_KEY", "TIVI_TMDB_API_KEY", "") + "\"")
                }

                release {
                    signingConfig = signingConfigs[if (useReleaseKeystore) "release" else "debug"]
                    isShrinkResources = true
                    isMinifyEnabled = true
                    proguardFiles("proguard-rules.pro")

                    buildConfigField("String", "TRAKT_CLIENT_ID", "\"" + propOrDef("TIVI_TRAKT_CLIENT_ID", "") + "\"")
                    buildConfigField("String", "TRAKT_CLIENT_SECRET", "\"" + propOrDef("TIVI_TRAKT_CLIENT_SECRET", "") + "\"")
                    buildConfigField("String", "TMDB_API_KEY", "\"" + propOrDef("TIVI_TMDB_API_KEY", "") + "\"")
                }

                create("benchmark") {
                    initWith(buildTypes["release"])
                    signingConfig = signingConfigs["debug"]
                    matchingFallbacks += "release"
                    proguardFiles("benchmark-rules.pro")
                }
            }

            flavorDimensions += "mode"
            productFlavors {
                create("qa") {
                    dimension = "mode"
                    // This is a build with Chucker enabled
                    proguardFiles("proguard-rules-chucker.pro")
                    versionNameSuffix = "-qa"
                }

                create("standard") {
                    dimension = "mode"
                    // Standard build is always ahead of the QA builds as it goes straight to
                    // the alpha channel. This is the 'release' flavour
                    versionCode = (android.defaultConfig.versionCode ?: 0) + 1
                }
            }
        }

        androidComponents {
            // Ignore the QA Benchmark variant
            val qaBenchmark = selector()
                .withBuildType("benchmark")
                .withFlavor("mode" to "qa")
            beforeVariants(qaBenchmark) { variant ->
                variant.enable = false
            }

            // Ignore the standardDebug variant
            val standard = selector()
                .withBuildType("debug")
                .withFlavor("mode" to "standard")
            beforeVariants(standard) { variant ->
                variant.enable = false
            }
        }

        tasks.withType<KotlinCompilationTask<*>> {
            compilerOptions {
                freeCompilerArgs.add("-opt-in=com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi")
            }
        }

        dependencies {
            ksp(libs.kotlininject.compiler)

            implementation(projects.api.tmdb)
            implementation(projects.api.trakt)
            implementation(projects.common.imageloading)
            implementation(projects.common.ui.compose)
            implementation(projects.common.ui.view)
            implementation(projects.core.analytics)
            implementation(projects.core.base)
            implementation(projects.core.logging)
            implementation(projects.core.performance)
            implementation(projects.core.powercontroller)
            implementation(projects.core.preferences)
            implementation(projects.data.dbSqldelight)
            implementation(projects.domain)
            implementation(projects.tasks.android)
            implementation(projects.ui.account)
            implementation(projects.ui.discover)
            implementation(projects.ui.episode.details)
            implementation(projects.ui.episode.track)
            implementation(projects.ui.library)
            implementation(projects.ui.popular)
            implementation(projects.ui.recommended)
            implementation(projects.ui.search)
            implementation(projects.ui.settings)
            implementation(projects.ui.show.details)
            implementation(projects.ui.show.seasons)
            implementation(projects.ui.trending)
            implementation(projects.ui.upnext)

            implementation(libs.accompanist.navigation.animation)
            implementation(libs.accompanist.navigation.material)
            implementation(libs.androidx.activity.activity)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.emoji)
            implementation(libs.androidx.lifecycle.viewmodel.ktx)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.androidx.profileinstaller)
            implementation(libs.compose.animation.animation)
            implementation(libs.compose.foundation.foundation)
            implementation(libs.compose.foundation.layout)
            implementation(libs.compose.material.iconsext)
            implementation(libs.compose.material.material)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui.tooling)
            implementation(libs.google.firebase.crashlytics)
            implementation(libs.kotlin.coroutines.android)
            implementation(libs.okhttp.loggingInterceptor)
            implementation(libs.timber)

            lintChecks(libs.slack.lint.compose)

            qaImplementation(libs.chucker.library)
            qaImplementation(libs.debugdrawer.debugdrawer)
            qaImplementation(libs.debugdrawer.okhttplogger)
            qaImplementation(libs.debugdrawer.timber)
            qaImplementation(libs.leakCanary)

            testImplementation(libs.androidx.test.core)
            testImplementation(libs.androidx.test.rules)
            testImplementation(libs.junit)
            testImplementation(libs.robolectric)
        }

        android.applicationVariants.forEach { variant ->
            tasks.create("open${'$'}{variant.name.capitalize()}") {
                dependsOn(tasks.named("install${'$'}{variant.name.capitalize()}"))

                doLast {
                    exec {
                        commandLine = "adb shell monkey -p ${'$'}{variant.applicationId} -c android.intent.category.LAUNCHER 1".split(" ")
                    }
                }
            }
        }

        if (file("google-services.json").exists()) {
            apply(plugin = libs.plugins.gms.googleServices.get().pluginId)
            apply(plugin = libs.plugins.firebase.crashlytics.get().pluginId)

            // Disable uploading mapping files for the benchmark build type
            android.buildTypes.getByName("benchmark") {
                configure<com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension> {
                    mappingFileUploadEnabled = false
                }
            }
        }

        fun <T : Any> propOrDef(propertyName: String, defaultValue: T): T {
            @Suppress("UNCHECKED_CAST")
            val propertyValue = project.properties[propertyName] as T?
            return propertyValue ?: defaultValue
        }

        fun <T : Any> propOrDef(propertyName: String, fallbackProperty: String, defaultValue: T): T {
            @Suppress("UNCHECKED_CAST")
            return project.properties[propertyName] as T?
                ?: project.properties[fallbackProperty] as T?
                ?: defaultValue
        }

        fun DependencyHandler.qaImplementation(dependencyNotation: Any) =
            add("qaImplementation", dependencyNotation)
    """.trimIndent()

    val QK_SMS_APP_GRADLE_FILE = """
        /*
         * Copyright (C) 2017 Moez Bhatti <moez.bhatti@gmail.com>
         *
         * This file is part of QKSMS.
         *
         * QKSMS is free software: you can redistribute it and/or modify
         * it under the terms of the GNU General Public License as published by
         * the Free Software Foundation, either version 3 of the License, or
         * (at your option) any later version.
         *
         * QKSMS is distributed in the hope that it will be useful,
         * but WITHOUT ANY WARRANTY; without even the implied warranty of
         * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
         * GNU General Public License for more details.
         *
         * You should have received a copy of the GNU General Public License
         * along with QKSMS.  If not, see <http://www.gnu.org/licenses/>.
         */
        apply plugin: 'com.android.application'
        apply plugin: 'realm-android' // Realm needs to be before Kotlin or the build will fail
        apply plugin: 'kotlin-android'
        apply plugin: 'kotlin-android-extensions'
        apply plugin: 'kotlin-kapt'

        android {
            compileSdkVersion 29
            buildToolsVersion "29.0.3"
            flavorDimensions "analytics"

            defaultConfig {
                applicationId "com.moez.QKSMS"
                minSdkVersion 21
                targetSdkVersion 29
                versionCode 2218
                versionName "3.9.4"
                setProperty("archivesBaseName", "QKSMS-v${'$'}{versionName}")
                testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"

                setProperty("archivesBaseName", "QKSMS-v${'$'}{versionName}")
            }

            signingConfigs {
                release
            }

            buildTypes {
                release {
                    minifyEnabled true
                    shrinkResources true
                    proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
                    signingConfig signingConfigs.release
                }
            }

            compileOptions {
                sourceCompatibility 1.8
                targetCompatibility 1.8
            }

            kotlinOptions {
                jvmTarget = "1.8"
            }

            lintOptions {
                abortOnError false
            }

            productFlavors {
                withAnalytics { dimension "analytics" }
                noAnalytics { dimension "analytics" }
            }

            if (System.getenv("CI") == "true") {
                signingConfigs.release.storeFile = file("../keystore")
                signingConfigs.release.storePassword = System.getenv("keystore_password")
                signingConfigs.release.keyAlias = System.getenv("key_alias")
                signingConfigs.release.keyPassword = System.getenv("key_password")
            }
        }

        androidExtensions {
            experimental = true
        }

        configurations {
            noAnalyticsDebug
            noAnalyticsRelease
            withAnalyticsDebug
            withAnalyticsRelease
        }

        dependencies {
            compileOnly "javax.annotation:jsr250-api:1.0"

            implementation project(":android-smsmms")
            implementation project(":common")
            implementation project(':data')
            implementation project(':domain')

            implementation('com.googlecode.ez-vcard:ez-vcard:0.10.4', { exclude group: "org.jsoup", module: "jsoup" exclude group: "org.freemarker", module: "freemarker" exclude group: "com.fasterxml.jackson.core", module: "jackson-core" }) 

            implementation "androidx.appcompat:appcompat:${'$'}androidx_appcompat_version"
            implementation "androidx.constraintlayout:constraintlayout:${'$'}androidx_constraintlayout_version"
            implementation "androidx.core:core-ktx:${'$'}androidx_core_version"
            implementation "androidx.emoji:emoji-appcompat:${'$'}androidx_emoji_version"
            implementation "androidx.lifecycle:lifecycle-common-java8:${'$'}lifecycle_version"
            implementation "androidx.lifecycle:lifecycle-extensions:${'$'}lifecycle_version"
            implementation "androidx.viewpager2:viewpager2:${'$'}androidx_viewpager_version"
            implementation "com.bluelinelabs:conductor-archlifecycle:${'$'}conductor_version"
            implementation "com.bluelinelabs:conductor:${'$'}conductor_version"
            implementation "com.f2prateek.rx.preferences2:rx-preferences:${'$'}rx_preferences_version"
            implementation "com.github.bumptech.glide:glide:${'$'}glide_version"
            implementation "com.github.chrisbanes:PhotoView:2.0.0"
            implementation "com.google.android.exoplayer:exoplayer-core:${'$'}exoplayer_version"
            implementation "com.google.android.material:material:${'$'}material_version"
            implementation "com.google.android:flexbox:0.3.1"
            implementation "com.google.dagger:dagger-android-support:${'$'}dagger_version"
            implementation "com.google.dagger:dagger:${'$'}dagger_version"
            implementation "com.jakewharton.rxbinding2:rxbinding-kotlin:${'$'}rxbinding_version"
            implementation "com.jakewharton.rxbinding2:rxbinding-support-v4-kotlin:${'$'}rxbinding_version"
            implementation "com.jakewharton.timber:timber:${'$'}timber_version"
            implementation "com.squareup.moshi:moshi-kotlin:${'$'}moshi_version"
            implementation "com.squareup.moshi:moshi:${'$'}moshi_version"
            implementation "com.uber.autodispose:autodispose-android-archcomponents-test:${'$'}autodispose_version"
            implementation "com.uber.autodispose:autodispose-android-archcomponents:${'$'}autodispose_version"
            implementation "com.uber.autodispose:autodispose-android:${'$'}autodispose_version"
            implementation "com.uber.autodispose:autodispose-lifecycle:${'$'}autodispose_version"
            implementation "com.uber.autodispose:autodispose:${'$'}autodispose_version"
            implementation "com.uber.rxdogtag:rxdogtag-autodispose:${'$'}rxdogtag_version"
            implementation "com.uber.rxdogtag:rxdogtag:${'$'}rxdogtag_version"
            implementation "io.reactivex.rxjava2:rxandroid:${'$'}rxandroid_version"
            implementation "io.reactivex.rxjava2:rxjava:${'$'}rxjava_version"
            implementation "io.reactivex.rxjava2:rxkotlin:${'$'}rxkotlin_version"
            implementation "me.leolin:ShortcutBadger:1.1.22"
            implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${'$'}kotlin_version"
            implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:${'$'}coroutines_version"
            implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:${'$'}coroutines_version"
            implementation "org.jetbrains.kotlinx:kotlinx-coroutines-reactive:${'$'}coroutines_version"
            implementation "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:${'$'}coroutines_version"

            implementation("com.google.android.exoplayer:exoplayer-ui:${'$'}exoplayer_version", { exclude group: "com.android.support", module: "support-media-compat" }) 
            implementation("io.realm:android-adapters:${'$'}realm_adapters_version") { transitive = false }

            kapt "com.github.bumptech.glide:compiler:${'$'}glide_version"
            kapt "com.google.dagger:dagger-android-processor:${'$'}dagger_version"
            kapt "com.google.dagger:dagger-compiler:${'$'}dagger_version"
            kapt "io.realm:realm-annotations-processor:${'$'}realm_version"
            kapt "io.realm:realm-annotations:${'$'}realm_version"

            kaptRelease "com.squareup.moshi:moshi-kotlin-codegen:${'$'}moshi_version"

            debugImplementation "com.squareup.moshi:moshi-kotlin:${'$'}moshi_version"

            androidTestImplementation("androidx.test.espresso:espresso-core:${'$'}espresso_version", { exclude group: "com.android.support", module: "support-annotations" }) 

            androidTestImplementation "org.mockito:mockito-android:${'$'}mockito_version"

            testImplementation "androidx.test:runner:${'$'}androidx_testrunner_version"
            testImplementation "junit:junit:${'$'}junit_version"
            testImplementation "org.mockito:mockito-core:${'$'}mockito_version"
            noAnalyticsDebug project(path: ':data', configuration: 'noAnalyticsDebug')
            noAnalyticsRelease project(path: ':data', configuration: 'noAnalyticsRelease')
            withAnalyticsDebug project(path: ':data', configuration: 'withAnalyticsDebug')
            withAnalyticsImplementation "com.android.billingclient:billing-ktx:${'$'}billing_version"
            withAnalyticsImplementation "com.android.billingclient:billing:${'$'}billing_version"
            withAnalyticsImplementation 'com.google.firebase:firebase-crashlytics:17.3.0'
            withAnalyticsRelease project(path: ':data', configuration: 'withAnalyticsRelease')
        }

        if (getGradle().getStartParameter().getTaskRequests().toString().contains("WithAnalytics")) {
            apply plugin: 'com.google.gms.google-services'
            apply plugin: 'com.google.firebase.crashlytics'
        }
    """.trimIndent()

    val QKSMS_APP_GRADLE_FILE_WITH_VARIABLE = """
        /*
         * Copyright (C) 2017 Moez Bhatti <moez.bhatti@gmail.com>
         *
         * This file is part of QKSMS.
         *
         * QKSMS is free software: you can redistribute it and/or modify
         * it under the terms of the GNU General Public License as published by
         * the Free Software Foundation, either version 3 of the License, or
         * (at your option) any later version.
         *
         * QKSMS is distributed in the hope that it will be useful,
         * but WITHOUT ANY WARRANTY; without even the implied warranty of
         * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
         * GNU General Public License for more details.
         *
         * You should have received a copy of the GNU General Public License
         * along with QKSMS.  If not, see <http://www.gnu.org/licenses/>.
         */
        apply plugin: 'com.android.application'
        apply plugin: 'realm-android' // Realm needs to be before Kotlin or the build will fail
        apply plugin: 'kotlin-android'
        apply plugin: 'kotlin-android-extensions'
        apply plugin: 'kotlin-kapt'

        android {
            compileSdkVersion 29
            buildToolsVersion "29.0.3"
            flavorDimensions "analytics"

            defaultConfig {
                applicationId "com.moez.QKSMS"
                minSdkVersion 21
                targetSdkVersion 29
                versionCode 2218
                versionName "3.9.4"
                setProperty("archivesBaseName", "QKSMS-v${'$'}{versionName}")
                testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"

                setProperty("archivesBaseName", "QKSMS-v${'$'}{versionName}")
            }

            signingConfigs {
                release
            }

            buildTypes {
                release {
                    minifyEnabled true
                    shrinkResources true
                    proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
                    signingConfig signingConfigs.release
                }
            }

            compileOptions {
                sourceCompatibility 1.8
                targetCompatibility 1.8
            }

            kotlinOptions {
                jvmTarget = "1.8"
            }

            lintOptions {
                abortOnError false
            }

            productFlavors {
                withAnalytics { dimension "analytics" }
                noAnalytics { dimension "analytics" }
            }

            if (System.getenv("CI") == "true") {
                signingConfigs.release.storeFile = file("../keystore")
                signingConfigs.release.storePassword = System.getenv("keystore_password")
                signingConfigs.release.keyAlias = System.getenv("key_alias")
                signingConfigs.release.keyPassword = System.getenv("key_password")
            }
        }

        androidExtensions {
            experimental = true
        }

        configurations {
            noAnalyticsDebug
            noAnalyticsRelease
            withAnalyticsDebug
            withAnalyticsRelease
        }

        dependencies {
            def glideVerison = "1.0.0"

            compileOnly "javax.annotation:jsr250-api:1.0"

            implementation project(":android-smsmms")
            implementation project(":common")
            implementation project(':data')
            implementation project(':domain')

            implementation('com.googlecode.ez-vcard:ez-vcard:0.10.4', { exclude group: "org.jsoup", module: "jsoup" exclude group: "org.freemarker", module: "freemarker" exclude group: "com.fasterxml.jackson.core", module: "jackson-core" }) 

            implementation "androidx.appcompat:appcompat:${'$'}androidx_appcompat_version"
            implementation "androidx.constraintlayout:constraintlayout:${'$'}androidx_constraintlayout_version"
            implementation "androidx.core:core-ktx:${'$'}androidx_core_version"
            implementation "androidx.emoji:emoji-appcompat:${'$'}androidx_emoji_version"
            implementation "androidx.lifecycle:lifecycle-common-java8:${'$'}lifecycle_version"
            implementation "androidx.lifecycle:lifecycle-extensions:${'$'}lifecycle_version"
            implementation "androidx.viewpager2:viewpager2:${'$'}androidx_viewpager_version"
            implementation "com.bluelinelabs:conductor-archlifecycle:${'$'}conductor_version"
            implementation "com.bluelinelabs:conductor:${'$'}conductor_version"
            implementation "com.f2prateek.rx.preferences2:rx-preferences:${'$'}rx_preferences_version"
            implementation "com.github.bumptech.glide:glide:${'$'}glideVerison"
            implementation "com.github.chrisbanes:PhotoView:2.0.0"
            implementation "com.google.android.exoplayer:exoplayer-core:${'$'}exoplayer_version"
            implementation "com.google.android.material:material:${'$'}material_version"
            implementation "com.google.android:flexbox:0.3.1"
            implementation "com.google.dagger:dagger-android-support:${'$'}dagger_version"
            implementation "com.google.dagger:dagger:${'$'}dagger_version"
            implementation "com.jakewharton.rxbinding2:rxbinding-kotlin:${'$'}rxbinding_version"
            implementation "com.jakewharton.rxbinding2:rxbinding-support-v4-kotlin:${'$'}rxbinding_version"
            implementation "com.jakewharton.timber:timber:${'$'}timber_version"
            implementation "com.squareup.moshi:moshi-kotlin:${'$'}moshi_version"
            implementation "com.squareup.moshi:moshi:${'$'}moshi_version"
            implementation "com.uber.autodispose:autodispose-android-archcomponents-test:${'$'}autodispose_version"
            implementation "com.uber.autodispose:autodispose-android-archcomponents:${'$'}autodispose_version"
            implementation "com.uber.autodispose:autodispose-android:${'$'}autodispose_version"
            implementation "com.uber.autodispose:autodispose-lifecycle:${'$'}autodispose_version"
            implementation "com.uber.autodispose:autodispose:${'$'}autodispose_version"
            implementation "com.uber.rxdogtag:rxdogtag-autodispose:${'$'}rxdogtag_version"
            implementation "com.uber.rxdogtag:rxdogtag:${'$'}rxdogtag_version"
            implementation "io.reactivex.rxjava2:rxandroid:${'$'}rxandroid_version"
            implementation "io.reactivex.rxjava2:rxjava:${'$'}rxjava_version"
            implementation "io.reactivex.rxjava2:rxkotlin:${'$'}rxkotlin_version"
            implementation "me.leolin:ShortcutBadger:1.1.22"
            implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${'$'}kotlin_version"
            implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:${'$'}coroutines_version"
            implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:${'$'}coroutines_version"
            implementation "org.jetbrains.kotlinx:kotlinx-coroutines-reactive:${'$'}coroutines_version"
            implementation "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:${'$'}coroutines_version"

            implementation("com.google.android.exoplayer:exoplayer-ui:${'$'}exoplayer_version", { exclude group: "com.android.support", module: "support-media-compat" }) 
            implementation("io.realm:android-adapters:${'$'}realm_adapters_version") { transitive = false }

            kapt "com.github.bumptech.glide:compiler:${'$'}glideVerison"
            kapt "com.google.dagger:dagger-android-processor:${'$'}dagger_version"
            kapt "com.google.dagger:dagger-compiler:${'$'}dagger_version"
            kapt "io.realm:realm-annotations-processor:${'$'}realm_version"
            kapt "io.realm:realm-annotations:${'$'}realm_version"

            kaptRelease "com.squareup.moshi:moshi-kotlin-codegen:${'$'}moshi_version"

            debugImplementation "com.squareup.moshi:moshi-kotlin:${'$'}moshi_version"

            androidTestImplementation("androidx.test.espresso:espresso-core:${'$'}espresso_version", { exclude group: "com.android.support", module: "support-annotations" }) 

            androidTestImplementation "org.mockito:mockito-android:${'$'}mockito_version"

            testImplementation "androidx.test:runner:${'$'}androidx_testrunner_version"
            testImplementation "junit:junit:${'$'}junit_version"
            testImplementation "org.mockito:mockito-core:${'$'}mockito_version"
            noAnalyticsDebug project(path: ':data', configuration: 'noAnalyticsDebug')
            noAnalyticsRelease project(path: ':data', configuration: 'noAnalyticsRelease')
            withAnalyticsDebug project(path: ':data', configuration: 'withAnalyticsDebug')
            withAnalyticsImplementation "com.android.billingclient:billing-ktx:${'$'}billing_version"
            withAnalyticsImplementation "com.android.billingclient:billing:${'$'}billing_version"
            withAnalyticsImplementation 'com.google.firebase:firebase-crashlytics:17.3.0'
            withAnalyticsRelease project(path: ':data', configuration: 'withAnalyticsRelease')
        }

        if (getGradle().getStartParameter().getTaskRequests().toString().contains("WithAnalytics")) {
            apply plugin: 'com.google.gms.google-services'
            apply plugin: 'com.google.firebase.crashlytics'
        }

    """.trimIndent()

    val NOW_IN_ANDROID_APP_GRADLE_KTS_WITH_VARIABLE = """
        /*
         * Copyright 2022 The Android Open Source Project
         *
         * Licensed under the Apache License, Version 2.0 (the "License");
         * you may not use this file except in compliance with the License.
         * You may obtain a copy of the License at
         *
         *     https://www.apache.org/licenses/LICENSE-2.0
         *
         * Unless required by applicable law or agreed to in writing, software
         * distributed under the License is distributed on an "AS IS" BASIS,
         * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
         * See the License for the specific language governing permissions and
         * limitations under the License.
         */
        import com.google.samples.apps.nowinandroid.NiaBuildType
        
        plugins {
            id("nowinandroid.android.application")
            id("nowinandroid.android.application.compose")
            id("nowinandroid.android.application.flavors")
            id("nowinandroid.android.application.jacoco")
            id("nowinandroid.android.hilt")
            id("jacoco")
            id("nowinandroid.android.application.firebase")
            id("com.google.android.gms.oss-licenses-plugin")
        }
        
        android {
            defaultConfig {
                applicationId = "com.google.samples.apps.nowinandroid"
                versionCode = 5
                versionName = "0.0.5" // X.Y.Z; X = Major, Y = minor, Z = Patch level
        
                // Custom test runner to set up Hilt dependency graph
                testInstrumentationRunner = "com.google.samples.apps.nowinandroid.core.testing.NiaTestRunner"
                vectorDrawables {
                    useSupportLibrary = true
                }
            }
        
            buildTypes {
                debug {
                    applicationIdSuffix = NiaBuildType.DEBUG.applicationIdSuffix
                }
                val release by getting {
                    isMinifyEnabled = true
                    applicationIdSuffix = NiaBuildType.RELEASE.applicationIdSuffix
                    proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        
                    // To publish on the Play store a private signing key is required, but to allow anyone
                    // who clones the code to sign and run the release variant, use the debug signing key.
                    // TODO: Abstract the signing configuration to a separate file to avoid hardcoding this.
                    signingConfig = signingConfigs.getByName("debug")
                }
                create("benchmark") {
                    // Enable all the optimizations from release build through initWith(release).
                    initWith(release)
                    matchingFallbacks.add("release")
                    // Debug key signing is available to everyone.
                    signingConfig = signingConfigs.getByName("debug")
                    // Only use benchmark proguard rules
                    proguardFiles("benchmark-rules.pro")
                    isMinifyEnabled = true
                    applicationIdSuffix = NiaBuildType.BENCHMARK.applicationIdSuffix
                }
            }
        
            packaging {
                resources {
                    excludes.add("/META-INF/{AL2.0,LGPL2.1}")
                }
            }
            testOptions {
                unitTests {
                    isIncludeAndroidResources = true
                }
            }
            namespace = "com.google.samples.apps.nowinandroid"
        }
        
        dependencies {
            val glideVersion = "4.8.0"
        
            implementation(project(":core:analytics"))
            implementation(project(":core:common"))
            implementation(project(":core:data"))
            implementation(project(":core:designsystem"))
            implementation(project(":core:model"))
            implementation(project(":core:ui"))
            implementation(project(":feature:bookmarks"))
            implementation(project(":feature:foryou"))
            implementation(project(":feature:interests"))
            implementation(project(":feature:search"))
            implementation(project(":feature:settings"))
            implementation(project(":feature:topic"))
            implementation(project(":sync:work"))
        
            implementation(libs.accompanist.systemuicontroller)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.compose.material3.windowSizeClass)
            implementation(libs.androidx.compose.runtime)
            implementation(libs.androidx.compose.runtime.tracing)
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.core.splashscreen)
            implementation(libs.androidx.hilt.navigation.compose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.androidx.profileinstaller)
            implementation(libs.androidx.window.manager)
            implementation(libs.coil.kt)
        
            implementation "com.github.bumptech.glide:glide:${'$'}glideVersion"
        
            kapt "com.github.bumptech.glide:compiler:${'$'}glideVersion"
        
            debugImplementation(project(":ui-test-hilt-manifest"))
        
            debugImplementation(libs.androidx.compose.ui.testManifest)
        
            androidTestImplementation(project(":core:data-test"))
            androidTestImplementation(project(":core:datastore-test"))
            androidTestImplementation(project(":core:network"))
            androidTestImplementation(project(":core:testing"))
        
            androidTestImplementation(kotlin("test"))
        
            androidTestImplementation(libs.accompanist.testharness)
            androidTestImplementation(libs.androidx.navigation.testing)
        }
        
        // androidx.test is forcing JUnit, 4.12. This forces it to use 4.13
        configurations.configureEach {
            resolutionStrategy {
                force(libs.junit4)
                // Temporary workaround for https://issuetracker.google.com/174733673
                force("org.objenesis:objenesis:2.6")
            }
        }

    """.trimIndent()
}
