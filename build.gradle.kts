plugins { 
    id("com.android.application") 
  }

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.networkmonitor"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        release {
            isMinified = false
        }
    }
}

dependencies { 
    implementation("androidx.core:core-ktx:1.10.0") 
    implementation("androidx.appcompat:appcompat:1.6.1") 
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2") 
    implementation("androidx.telephony:telephony:1.0.0") 
    implementation("androidx.systemservice:systemservice:1.0.0") 
}