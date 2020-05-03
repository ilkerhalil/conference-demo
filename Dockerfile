FROM docker.io/maven
EXPOSE 5000
RUN mkdir app
COPY target/*.jar /app/conference-demo.jar
WORKDIR /app
ENTRYPOINT [ "java","-jar","conference-demo.jar"]
