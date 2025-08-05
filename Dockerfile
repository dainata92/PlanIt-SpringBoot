FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

# Rendre le script exécutable
RUN chmod +x ./mvnw

# Utiliser Maven Wrapper pour construire le projet
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

# Lancer l'application avec le bon nom du JAR
CMD ["java", "-jar", "target/mon-app.jar"]
