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

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    flavorDimensions += listOf("releasing")

    productFlavors {
        create("production") {
            dimension = "releasing"
            manifestPlaceholders["appIcon"] = "@mipmap/icon_production"
            manifestPlaceholders["roundIcon"] = "@mipmap/icon_production_round"
        }
        create("development") {
            applicationIdSuffix = ".development"
            dimension = "releasing"
            manifestPlaceholders["appIcon"] = "@mipmap/icon_development"
            manifestPlaceholders["roundIcon"] = "@mipmap/icon_development_round"
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
    implementation(project(":phone:core:resource"))
    implementation(project(":phone:core:application"))
    implementation(project(":phone:model"))
    implementation(project(":phone:repository:interfaces"))
    implementation(project(":phone:features:onboarding"))
    implementation(project(":phone:features:ranking"))
    implementation(project(":phone:features:traveling"))
    implementation(project(":phone:core:navigation"))
    implementation(project(":wear:service"))
    implementation(libs.kotlinx.coroutine.android)
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)
    implementation(libs.androidx.navigation)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.bundles.maps)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    lintChecks(libs.slack.compose.lints)
}