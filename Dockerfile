#FROM registry.gitlab.com/itic4/common-images:jdk-0.0.1
FROM amazoncorretto:17
COPY target/talentmanagement-0.0.1.jar app.jar
CMD ["java", "-jar", "app.jar"]
