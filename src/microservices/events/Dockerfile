FROM bellsoft/liberica-openjdk-alpine:latest
RUN mkdir /opt/app
COPY target/events-service.jar /opt/app
CMD ["java", "-jar", "/opt/app/events-service.jar"]