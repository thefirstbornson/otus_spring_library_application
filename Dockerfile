FROM openjdk:8-jdk-alpine
EXPOSE 8080:8080
ADD target/library.jar library.jar
CMD [ "java","-jar", "library.jar" ]
