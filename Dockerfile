FROM openjdk:8-alpine
RUN mkdir app
COPY target/*.jar /app/conference-demo.jar
WORKDIR /app
EXPOSE 5000
ENTRYPOINT [ "java","-jar","conference-demo.jar"] 
