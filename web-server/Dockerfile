FROM openjdk:11 as BUILD
COPY . .
RUN ./gradlew bootJar

FROM openjdk:11-jre
COPY --from=BUILD build/libs/web-server.jar /usr/src/elastic-apm-web-server/app.jar
WORKDIR /usr/src/elastic-apm-web-server
EXPOSE 8080
CMD ["java","-jar","app.jar"]