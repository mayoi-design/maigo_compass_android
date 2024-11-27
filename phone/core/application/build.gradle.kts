apply(from = "secrets.gradle.kts")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    buildFeatures.buildConfig = true
    namespace = "jp.ac.mayoi.core.application"
    compileSdk = 35

    defaultConfig {
        minSdk = 30

        val apiUrl: String =
            System.getenv("MAIGO_COMPASS_API_URL")
                ?: project.extra["MAIGO_COMPASS_API_URL"] as String
        buildConfigField("String", "MAIGO_COMPASS_API_URL", "\"$apiUrl\"")

        val apiKey: String =
            System.getenv("MAIGO_COMPASS_API_KEY")
                ?: project.extra["MAIGO_COMPASS_API_KEY"] as String
        buildConfigField("String", "MAIGO_COMPASS_API_KEY", "\"$apiKey\"")

        val mapsApiKey: String =
            System.getenv("MAPS_API_KEY") ?: project.extra["MAPS_API_KEY"] as String
        resValue("string", "GOOGLE_MAPS_API_KEY", mapsApiKey)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
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
    implementation(project(":phone:service:interfaces"))
    implementation(project(":phone:repository:interfaces"))
    implementation(project(":phone:core:datastore"))
    implementation(project(":phone:repository:implementations"))
    implementation(project(":phone:features:onboarding"))
    implementation(project(":phone:features:traveling"))

    implementation(libs.bundles.composeKit)
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.okhttp3.interceptor)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp3)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.kotlinx)
    implementation(libs.play.services.wearable)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    lintChecks(libs.slack.compose.lints)
}