FROM docker.io/library/eclipse-temurin:21-jdk-alpine AS builder

RUN apk add --no-cache nodejs npm

WORKDIR /src/gametime
COPY . .
RUN npm install

RUN npx tailwindcss -i src/main/resources/static/input.css -o src/main/resources/static/output.css

RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar

FROM docker.io/library/eclipse-temurin:21-jre-alpine AS runner

ARG USER_NAME=gametime
ARG USER_UID=1000
ARG USER_GID=${USER_UID}

RUN addgroup -g ${USER_GID} ${USER_NAME} && \
    adduser -h /opt/gametime -D -u ${USER_UID} -G ${USER_NAME} ${USER_NAME}

USER ${USER_NAME}
WORKDIR /opt/gametime
COPY --from=builder --chown=${USER_UID}:${USER_GID} /src/gametime/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java"]
CMD ["-jar", "app.jar", "--server.port=8000"]