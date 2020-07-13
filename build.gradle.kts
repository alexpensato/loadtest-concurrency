plugins {
	id("org.springframework.boot") version "2.2.8.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	war
}

group = "org.pensatocode.loadtest"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// Spring Web
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")

	// Spring Data
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("org.postgresql:postgresql")

	// Lombok
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
