import com.javdc.napptiloompas.AppConfig
import com.javdc.napptiloompas.dependencies.Versions

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("kotlin")
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
}