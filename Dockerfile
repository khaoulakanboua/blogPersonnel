FROM openjdk:17
WORKDIR /App

COPY /target/blogProject-0.0.1-SNAPSHOT.jar .


ENTRYPOINT ["java", "-jar" , "blogProject-0.0.1-SNAPSHOT.jar"]