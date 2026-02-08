plugins {
    id("com.android.application") version "8.11.1" apply false // might need to be different: https://developer.android.com/studio/releases#android_gradle_plugin_and_android_studio_compatibility
    id("org.jetbrains.kotlin.android") version "2.2.10" apply false
    id("com.google.devtools.ksp").version("2.2.10-2.0.2") apply false
    id("com.google.dagger.hilt.android") version "2.57" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.10" apply false
}