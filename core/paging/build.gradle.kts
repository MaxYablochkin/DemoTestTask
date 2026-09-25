plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.koin.compiler)
}

android {
    namespace = "com.dev.maxyablochkin.demotaskapp.core.paging"
    compileSdk {
        version = release(37)
    }
    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    api(libs.androidx.paging.runtime)

    implementation(projects.core.database)
    implementation(projects.core.network)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
}