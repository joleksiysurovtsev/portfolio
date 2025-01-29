FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY . .
RUN ./gradlew build -x test

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar"]