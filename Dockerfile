FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN true
COPY src ./src
RUN ./mvnw -q -DskipTests package || true
EXPOSE 8080
CMD ["sh", "-c", "./mvnw spring-boot:run"]
