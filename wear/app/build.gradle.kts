plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "jp.ac.mayoi.maigocompass"
    compileSdk = 35

    defaultConfig {
        applicationId = "jp.ac.mayoi.maigocompass"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    lint {
        disable += "ExtraTranslation"
        disable += "ObsoleteLintCustomCheck"
        disable += "OldTargetApi"
        disable += "ComposeModifierMissing"
        abortOnError = false
        xmlReport = true
        xmlOutput = rootProject.file("./build-report/lint-results-${project.displayName}.xml")
    }
}

dependencies {
    implementation(project(":common:model"))
    implementation(project(":wear:core:resource"))
    implementation(project(":wear:core:application"))
    implementation(project(":wear:service"))
    implementation(project(":wear:features:traveling"))
    implementation(project(":wear:features:waiting"))
    implementation(project(":wear:repository:implementations"))

    implementation(libs.bundles.wearComposeKit)
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)
    implementation(project(":wear:core:navigation"))
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.tiles)
    implementation(libs.androidx.tiles.material)
    implementation(libs.horologist.compose.tools)
    implementation(libs.horologist.tiles)
    implementation(libs.androidx.watchface.complications.data.source.ktx)
    implementation(libs.androidx.navigation)
    implementation(libs.kotlinx.serialization.json)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    lintChecks(libs.slack.compose.lints)
}