plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "ja.ac.mayoi.traveling"
    compileSdk = 35
    defaultConfig {
        minSdk = 30
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

    lint {
        disable += "ObsoleteLintCustomCheck"
        disable += "ComposeModifierMissing"
        abortOnError = false
        xmlReport = true
        xmlOutput = rootProject.file("./build-report/lint-results-${project.displayName}.xml")
    }
}

dependencies {
    implementation(project(":common:model"))
    implementation(project(":phone:repository:interfaces"))
    implementation(project(":phone:core:util"))
    implementation(project(":phone:core:resource"))
    implementation(project(":common:resource"))
    implementation(project(":phone:model"))
    implementation(project(":wear:service"))
    implementation(libs.bundles.composeKit)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.material)
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)
    implementation(libs.play.services.wearable)
    implementation(libs.kotlinx.coroutine.core)
    implementation(libs.kotlinx.coroutine.android)
    implementation(libs.kotlinx.coroutine.play.services)
    implementation(libs.kotlinx.serialization.json)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.lifecycle.runtime.ktx)
    lintChecks(libs.slack.compose.lints)
}