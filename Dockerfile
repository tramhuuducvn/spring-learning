FROM amazoncorretto:17.0.10-al2023
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} learning-spring-app.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /learning-spring-app.jar"]