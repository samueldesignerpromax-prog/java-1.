FROM openjdk:17-jdk-slim
WORKDIR /app
COPY Server.java index.html ./
RUN javac Server.java
CMD ["java", "Server"]
