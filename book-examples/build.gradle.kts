description = "Códigos puros e fiéis ao livro do Marcos Brizeno"

configurations.testRuntimeClasspath {
    exclude(group = "org.slf4j", module = "slf4j-simple")
}

tasks.withType<Test> {
    environment("TESTCONTAINERS_RYUK_DISABLED", "true")
    environment("DOCKER_HOST", "unix:///var/run/docker.sock")
    jvmArgs("-Ddocker.host=unix:///var/run/docker.sock")
}

dependencies {
    val slf4jVersion = "2.0.17"

    implementation("commons-net:commons-net:3.11.1")
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    runtimeOnly("org.slf4j:slf4j-simple:$slf4jVersion")
    testImplementation("com.github.valfirst:slf4j-test:3.0.1")
    testImplementation("org.testcontainers:testcontainers:1.21.4")
    testImplementation("org.testcontainers:junit-jupiter:1.21.4")
    testImplementation("com.github.docker-java:docker-java-transport-httpclient5:3.4.1")
    testImplementation("org.assertj:assertj-core:3.27.3")
}