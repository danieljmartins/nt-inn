FROM openjdk:17-jdk-slim

# Forçando a instalação do Gradle, não estava reconhecendo
RUN apt-get update && \
    apt-get install -y wget unzip && \
    wget https://services.gradle.org/distributions/gradle-7.5-bin.zip && \
    unzip gradle-7.5-bin.zip -d /opt/ && \
    ln -s /opt/gradle-7.5/bin/gradle /usr/bin/gradle && \
    rm gradle-7.5-bin.zip

COPY --chown=gradle:gradle . .
WORKDIR .
RUN gradle build -x test --no-daemon
EXPOSE 8080
CMD ["gradle", "bootRun"]
