# Используем базовый образ OpenJDK 17 с JDK
FROM eclipse-temurin:17-jdk

# Указываем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем JAR-файл в контейнер
COPY target/ComplimentBot-0.0.1-SNAPSHOT.jar app.jar

# Указываем команду запуска
CMD ["java", "-jar", "app.jar"]
