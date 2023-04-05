# Imagen base
FROM openjdk:17-alpine


# Instalar PostgreSQL
RUN apk add --update --no-cache postgresql-client postgresql

# Crear usuario y base de datos
RUN apk add --no-cache su-exec && \
    mkdir /docker-entrypoint-initdb.d && \
    echo "CREATE DATABASE ip_geofinder;" > /docker-entrypoint-initdb.d/init.sql && \
    echo "CREATE USER ip_geofinder_user WITH ENCRYPTED PASSWORD '12345';" >> /docker-entrypoint-initdb.d/init.sql && \
    echo "GRANT ALL PRIVILEGES ON DATABASE ip_geofinder TO ip_geofinder_user;" >> /docker-entrypoint-initdb.d/init.sql

# Copiar el jar del microservicio
COPY build/libs/ipGeofinderLoad-0.0.1-SNAPSHOT.jar /app/ipGeofinderLoad-0.0.1-SNAPSHOT.jar

# Establecer el directorio de trabajo
WORKDIR /app

# Exponer el puerto del microservicio
EXPOSE 8080

# Ejecutar el microservicio
CMD ["java", "-jar", "ip-geofinderipGeofinderLoad-0.0.1-SNAPSHOT.jar"]
