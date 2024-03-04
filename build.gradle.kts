// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("de.mannodermaus.android-junit5") version "1.8.2.1" apply false
    id("com.google.devtools.ksp") version "1.9.21-1.0.15" apply false
//    id("org.jacoco.core") version "0.8.4" apply false
    java
}

//subprojects {

val implementation by configurations


dependencies {
    implementation("org.jacoco:org.jacoco.core:0.8.5")
}
//}
//const val JACOCO = "0.8.5"

//object PluginsVersions {
//    const val GRADLE_ANDROID = "4.1.0"
//    const val KOTLIN = "1.4.0"
//    const val GRADLE_CRASHLYTICS = "2.2.0"
//    const val GOOGLE_SERVICES = "4.3.3"
//    const val JACOCO = "0.8.5"
//    const val HILT = "2.28-alpha"
//}

//dependencies {
////    implementation("org.jacoco:org.jacoco.core:${JACOCO}")
//}

object PluginsVersions {
    const val GRADLE_ANDROID = "4.1.0"
    const val KOTLIN = "1.4.0"
    const val GRADLE_CRASHLYTICS = "2.2.0"
    const val GOOGLE_SERVICES = "4.3.3"
    const val JACOCO = "0.8.5"
    const val HILT = "2.28-alpha"
}

