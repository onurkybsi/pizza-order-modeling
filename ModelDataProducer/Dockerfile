FROM openjdk:11-jdk-slim
COPY target ./
ENTRYPOINT ["java","-jar","modelDataProducer.jar"]

# I know .jar compilation should be in the container :) Next time :)