FROM fabric8/s2i-java
EXPOSE 5000
RUN mkdir app
COPY maven/*.jar /app/conference-demo.jar
WORKDIR /app
ENTRYPOINT [ "java","-jar","conference-demo.jar"]
