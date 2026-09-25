plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.koin.compiler)
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
}
