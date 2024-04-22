// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48")
        classpath("de.mannodermaus.gradle.plugins:android-junit5:1.10.0.0")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:12.1.0")
        classpath("org.jacoco:org.jacoco.core:0.8.8")
    }
}

plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("de.mannodermaus.android-junit5") version "1.10.0.0" apply false
    id("com.google.devtools.ksp") version "1.9.21-1.0.15" apply false
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0" apply false
}
