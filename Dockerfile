FROM azul/zulu-openjdk-alpine:11.0.4

MAINTAINER 	Shamsuddin Tibriz

VOLUME /tmp

COPY target/rabobank-assignment-0.0.1-SNAPSHOT.jar rabobank-assignment.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Fix timezone (see https://serverfault.com/questions/683605/docker-container-time-timezone-will-not-reflect-changes)
ENV TZ=Europe/Amsterdam
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/rabobank-assignment.jar"]