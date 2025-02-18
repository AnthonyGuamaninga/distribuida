# docker build -t app-authors .
# docker build -t aaguamaninga/app-authors .
# docker push aaguamaninga/app-authors

FROM eclipse-temurin:21.0.5_11-jre-alpine

RUN mkdir -p /app/resources/db/migration
WORKDIR /app

COPY build/quarkus-app/app ./app
COPY build/quarkus-app/lib ./lib
COPY build/quarkus-app/quarkus ./quarkus
COPY build/quarkus-app/quarkus-run.jar app.jar

## Copiar los archivos de migración de Flyway
#COPY src/main/resources/db/migration/ /app/resources/db/migration/

CMD ["java", "-jar", "app.jar"]