FROM openjdk:11
COPY build/libs/web-server.jar /usr/src/elastic-apm-web-server/app.jar
WORKDIR /usr/src/elastic-apm-web-server
EXPOSE 8080
CMD ["java","-jar","app.jar"]