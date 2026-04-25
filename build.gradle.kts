import org.owasp.dependencycheck.gradle.extension.DependencyCheckExtension

plugins {
	java
	id("org.springframework.boot") version "4.0.6" apply false
	id("io.spring.dependency-management") version "1.1.7" apply false
	id("org.owasp.dependencycheck") version "12.2.1" apply false
	id("org.sonarqube") version "7.2.3.7755"
}

allprojects {
	group = "dev.weriton.patterns"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}
}

subprojects {
	apply(plugin = "java")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "jacoco")
	apply(plugin = "org.owasp.dependencycheck")

	java {
		sourceCompatibility = JavaVersion.VERSION_21
		targetCompatibility = JavaVersion.VERSION_21
	}

	dependencies {
		val lombokVersion = "1.18.46"
		val junitVersion = "6.0.3"
		val mockitoVersion = "5.23.0"

		compileOnly("org.projectlombok:lombok:$lombokVersion")
		annotationProcessor("org.projectlombok:lombok:$lombokVersion")

		testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
		testImplementation("org.mockito:mockito-core:$mockitoVersion")
		testImplementation("org.mockito:mockito-junit-jupiter:$mockitoVersion")

		testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
		testRuntimeOnly("org.junit.platform:junit-platform-launcher:$junitVersion")
	}

	tasks.withType<Test> {
		useJUnitPlatform()
		finalizedBy("jacocoTestReport")
	}

	tasks.named<JacocoReport>("jacocoTestReport") {
		dependsOn("test")
		reports {
			xml.required = true
			html.required = true
		}
	}

	tasks.named<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
		violationRules {
			rule {
				limit {
					minimum = "0.80".toBigDecimal()
				}
			}
		}
	}

	tasks.named("check") {
		dependsOn("jacocoTestCoverageVerification")
	}

	configure<DependencyCheckExtension> {
		failBuildOnCVSS = 7.0f

		nvd {
			apiKey = System.getenv("NVD_API_KEY") ?: ""
			delay = 3000
		}
		analyzers {
			ossIndexEnabled = false
		}
	}
}
