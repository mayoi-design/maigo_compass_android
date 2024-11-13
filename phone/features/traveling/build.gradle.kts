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
    implementation(project(":phone:core:util"))
    implementation(project(":phone:core:resource"))
    implementation(libs.bundles.composeKit)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.lifecycle.runtime.ktx)
    lintChecks(libs.slack.compose.lints)
}