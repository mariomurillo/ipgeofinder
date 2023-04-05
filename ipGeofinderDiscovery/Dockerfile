# Imagen base
FROM openjdk:17-alpine

# Copiar el jar del microservicio
COPY build/libs/ipGeofinderDiscovery-0.0.1-SNAPSHOT.jar /app/ipGeofinderDiscovery-0.0.1-SNAPSHOT.jar

# Establecer el directorio de trabajo
WORKDIR /app

# Exponer el puerto del microservicio
EXPOSE 8761

# Ejecutar el microservicio
CMD ["java", "-jar", "ipGeofinderDiscovery-0.0.1-SNAPSHOT.jar"]
