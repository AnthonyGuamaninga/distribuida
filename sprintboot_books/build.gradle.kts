plugins {
    id("java")
    id("io.quarkus") version "3.17.0"
    id("io.freefair.lombok") version "8.11"
}

group = "com.programacion.distribuida"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    //CDI
    //REST
    implementation("org.springframework.boot:spring-boot-starter-web:3.4.0")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.2.0")

    //CONSUL
    implementation("org.springframework.cloud:spring-cloud-starter-consul-discovery:4.2.0")

    //JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.4.0")
    implementation("org.postgresql:postgresql:42.7.4")

}
