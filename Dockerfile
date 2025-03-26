FROM eclipse-temurin:17-jdk

WORKDIR /app

# Собираем проект внутри контейнера, если JAR файл не был собран заранее
COPY . .
RUN mvn clean install -DskipTests

# Копируем JAR файл
COPY target/ComplimentBot-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
