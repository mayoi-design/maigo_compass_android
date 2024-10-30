plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "jp.ac.mayoi.core.datastore"
    compileSdk = 34

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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

    implementation(libs.androidx.preference.datastore)
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    lintChecks(libs.slack.compose.lints)
}