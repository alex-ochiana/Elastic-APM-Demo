FROM openjdk:11 as BUILD
COPY . .
RUN ./gradlew bootJar

FROM openjdk:11-jre
COPY --from=BUILD build/libs/web-server.jar /usr/src/elastic-apm-web-server/app.jar
WORKDIR /usr/src/elastic-apm-web-server
RUN wget https://repo1.maven.org/maven2/co/elastic/apm/elastic-apm-agent/1.22.0/elastic-apm-agent-1.22.0.jar
EXPOSE 8080
CMD ["java", \
    "-javaagent:elastic-apm-agent-1.22.0.jar", \
    "-Delastic.apm.service_name=web-server", \
    "-Delastic.apm.application_packages=com.oaa", \
    "-Delastic.apm.server_urls=http://apm-server:8200", \
    "-jar", \
    "app.jar"]