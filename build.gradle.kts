plugins {
    id("java")
    id("io.github.goooler.shadow") version "8.1.8"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("xyz.jpenilla.run-paper") version "2.2.4"
}

group = "dev.rollczi"
version = "1.0-SNAPSHOT"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

repositories {
    mavenCentral()
    maven("https://repo.panda-lang.org/releases/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.eternalcode.pl/releases")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21-R0.1-SNAPSHOT")

    implementation("dev.rollczi:litecommands-bukkit:${Versions.LITECOMMANDS}")
    implementation("dev.rollczi:litecommands-adventure:${Versions.LITECOMMANDS}")

    implementation("de.eldoria.jacksonbukkit:paper:${Versions.JACKSON_BUKKIT}")
    implementation("net.dzikoysk:cdn:${Versions.CDN}")
    implementation("org.panda-lang:panda-utilities:${Versions.PANDA_UTILITIES}")
    implementation("dev.piotrulla:craftinglib:${Versions.CRAFTINGLIB}")

    testImplementation(platform("org.junit:junit-bom:${Versions.JUNIT}"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:${Versions.ASSERTJ}")
    testImplementation("org.awaitility:awaitility:${Versions.AWAITILITY}")
}


val pluginName = "LiteCobbleX"
val packageName = "dev.rollczi.litecobblex"

bukkit {
    main = "$packageName.$pluginName"
    apiVersion = "1.13"
    author = "Rollczi"
    name = pluginName
    version = "${project.version}"
}

tasks.shadowJar {
    archiveFileName.set("$pluginName v${project.version}.jar")

    listOf(
        "panda.std",
        "dev.rollczi.litecommands",
    ).forEach { relocate(it, "$packageName.libs.$it") }
}

tasks.runServer {
    minecraftVersion("1.21")
}