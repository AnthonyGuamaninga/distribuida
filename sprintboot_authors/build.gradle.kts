plugins {
    id("java")
    id("io.freefair.lombok") version "8.11"
    id("org.springframework.boot") version "3.4.0"
}

group = "com.programacion.distribuida"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {

//    implementation("org.springframework.boot:spring-boot-dependencies:3.4.0")
//    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.1.5"))
//    CDI
    implementation("org.springframework.boot:spring-boot-starter:3.4.0")
//    REST
    implementation("org.springframework.boot:spring-boot-starter-web:3.4.0")
//    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")
//    implementation("jakarta.json.bind:jakarta.json.bind-api:2.0.0")
//    implementation("org.eclipse:yasson:3.0.0")
//    JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.4.0")
    implementation("org.postgresql:postgresql:42.7.4")
    //Discovery
    implementation("org.springframework.cloud:spring-cloud-starter-consul-discovery:4.2.0")
//    implementation("org.springframework.cloud:spring-cloud-starter-consul-config:4.2.0")

    //ACTUATOR
    implementation("org.springframework.boot:spring-boot-starter-actuator:3.4.0")

    //DEV
    implementation("org.springframework.boot:spring-boot-devtools:3.4.0")


}
