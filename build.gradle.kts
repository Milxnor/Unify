plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.shadow)
    alias(libs.plugins.runhytale)
    alias(libs.plugins.manifestfactory)
    alias(libs.plugins.serialization)
}

group = "net.moonlight"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.triumphteam.dev/snapshots")
}

dependencies {
    compileOnly(files("C:\\Hytale\\2026.01.13-dcad8778f\\Server\\HytaleServer.jar"))
    implementation(libs.stdlib)
    implementation(libs.serialization)
    implementation(libs.bundles.ktor)
    implementation(libs.jda)
    implementation(libs.polaris)
}

hytaleManifest {
    group.set("io.moonlightdevelopment")
    name.set("unify")
    main.set("io.moonlightdevelopment.unify.UnifyPlugin")
    description.set("A Discord Chat bridge for Hytale Servers.")
    authors {
        author {
            name.set("Simeon")
            website.set("https://moonlightdevelopment.io")
        }
    }
}