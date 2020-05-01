FROM docker.io/openjdk:8-alpine
EXPOSE 5000
RUN mkdir app
COPY maven/*.jar /app/conference-demo.jar
WORKDIR /app
ENTRYPOINT [ "java","-jar","conference-demo.jar"]
