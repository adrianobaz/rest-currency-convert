FROM amazoncorretto:11-alpine-jdk
MAINTAINER adrianobaz
COPY target/rest-currency-convert-1.0.0.jar rest-currency-convert-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/rest-currency-convert-1.0.0.jar"]