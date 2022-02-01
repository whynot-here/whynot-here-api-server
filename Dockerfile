FROM openjdk:11
RUN mkdir /app
ADD whynot-0.0.1-SNAPSHOT.jar /app
CMD [ "java", "-jar", "/app/whynot-0.0.1-SNAPSHOT.jar" ] 
