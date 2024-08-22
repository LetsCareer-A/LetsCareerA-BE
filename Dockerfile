FROM amd64/amazoncorretto:17

WORKDIR /app

COPY ./build/libs/letscareer-0.0.1-SNAPSHOT.jar /app/letscareer.jar

CMD ["java", "-Duser.timezone=Asia/Seoul", "-jar", "-Dspring.profiles.active=dev", "/app/letscareer.jar"]
