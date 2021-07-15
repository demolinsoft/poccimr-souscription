FROM openjdk:8
ADD target/microservice-souscription.jar microservice-souscription.jar
EXPOSE 8500
ENTRYPOINT ["java","-jar","microservice-souscription.jar"]