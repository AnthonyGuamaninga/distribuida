plugins {
    id("java")
    id("io.freefair.lombok") version "8.11"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.programacion.distribuida"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
//    CDI
    implementation("org.apache.openwebbeans:openwebbeans-spi:4.0.2")
    implementation("org.apache.openwebbeans:openwebbeans-impl:4.0.2")
//    JPA
    implementation("org.eclipse.persistence:eclipselink:4.0.3")

//    REST
    implementation("io.helidon.webserver:helidon-webserver:4.0.9")
    implementation("io.helidon.http.media:helidon-http-media-jsonb:4.0.9")
//    DB
    implementation("org.xerial:sqlite-jdbc:3.46.0.0")


}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "com.programacion.distribuida.rest.RestBook",
            "Class-Path" to configurations.runtimeClasspath.get()
                .joinToString(" ") { file -> file.name }
        )
    }
}