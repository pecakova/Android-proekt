plugins {
    alias(libs.plugins.android.application) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}

buildscript {
    repositories {
        google()  // Make sure this is included
        mavenCentral()
    }
    dependencies {
        classpath (libs.google.services)  // Add this line
    }
}
