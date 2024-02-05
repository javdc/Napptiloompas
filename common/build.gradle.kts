import com.javdc.napptiloompas.AppConfig
import com.javdc.napptiloompas.dependencies.Versions

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("com.google.devtools.ksp")
}

java {
    sourceCompatibility = AppConfig.javaSourceCompatibility
    targetCompatibility = AppConfig.javaTargetCompatibility
}

kotlin {
    jvmToolchain(AppConfig.kotlinJvmToolchainJdkVersion)
}

dependencies {
    implementation("co.touchlab:kermit:${Versions.kermitVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}")
    implementation("com.google.dagger:hilt-core:${Versions.hiltVersion}")
    ksp("com.google.dagger:hilt-compiler:${Versions.hiltVersion}")
}