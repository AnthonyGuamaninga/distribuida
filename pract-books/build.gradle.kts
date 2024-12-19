plugins {
    id("java")
    id("io.freefair.lombok") version "8.11" // Lombok
    id("com.github.johnrengelman.shadow") version "8.1.1" // Fat JAR
}

group = "com.programacion.distribuida"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
//    CDI
    implementation("org.apache.openwebbeans:openwebbeans-impl:4.0.2")
    implementation("org.apache.openwebbeans:openwebbeans-spi:4.0.2")

    implementation("org.apache.geronimo.specs:geronimo-atinject_1.0_spec:1.2")
    implementation("org.apache.geronimo.specs:geronimo-jcdi_2.0_spec:1.2")
    implementation("org.apache.geronimo.specs:geronimo-interceptor_1.2_spec:1.2")
    implementation("org.apache.geronimo.specs:geronimo-annotation_1.3_spec:1.3")

//    JPA
    implementation("org.eclipse.persistence:eclipselink:4.0.3")

//    REST
    implementation("io.helidon.webserver:helidon-webserver:4.0.9")
//    implementation("io.helidon.media:helidon-media-jsonb:4.0.9")
//    implementation("io.helidon.http.media:helidon-http-media-jsonb:4.0.9")

    implementation("com.google.code.gson:gson:2.10.1")

//    DB
    implementation("org.xerial:sqlite-jdbc:3.46.0.0")

}

sourceSets {
    main {
        output.setResourcesDir(file("${buildDir}/classes/java/main"))
    }
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "com.programacion.distribuida.rest.PrincipalRestBook",
            "Class-Path" to configurations.runtimeClasspath.get()
                .joinToString(" ") { file -> file.name }
        )
    }
}
