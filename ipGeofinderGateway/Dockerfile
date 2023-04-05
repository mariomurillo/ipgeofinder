# Imagen base
FROM openjdk:17-alpine

# Copiar el jar del microservicio
COPY build/libs/ipGeofinderGateway-0.0.1-SNAPSHOT.jar /app/ipGeofinderGateway-0.0.1-SNAPSHOT.jar

# Establecer el directorio de trabajo
WORKDIR /app

# Exponer el puerto del microservicio
EXPOSE 8080

# Ejecutar el microservicio
CMD ["java", "-jar", "ipGeofinderGateway-0.0.1-SNAPSHOT.jar"]
