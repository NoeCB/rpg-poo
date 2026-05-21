# Etapa 1: Compilación de la aplicación
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# Copiar archivos de configuración de Maven y descargar dependencias para aprovechar el almacenamiento en caché
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
# Asegurar permisos de ejecución en el wrapper de Maven
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

# Copiar el código fuente y compilar el archivo JAR
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Etapa 2: Imagen de ejecución ligera
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copiar el archivo JAR generado desde la etapa de compilación
COPY --from=builder /app/target/*.jar app.jar

# Configurar variables de entorno por defecto
ENV SPRING_DATASOURCE_URL=jdbc:mariadb://localhost:3306/dbd_rpg?useSSL=false&serverTimezone=UTC
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=
ENV SERVER_PORT=8080

EXPOSE 8080

# Iniciar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
