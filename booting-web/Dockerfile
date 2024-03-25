FROM alpine:3.19 as BUILDER
ARG VERSION=0.0.1-SNAPSHOT
WORKDIR /build/
COPY pom.xml /build
COPY src /build/src

RUN mvn clean package
COPY target/booting-web-${VERSION}.jar target/application.jar

FROM alpine:3.19
WORKDIR /app/

COPY --from=BUILDER /build/target/application.jar /app/
CMD java -jar /app/application.jar

