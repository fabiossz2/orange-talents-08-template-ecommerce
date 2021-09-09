FROM openjdk:11

WORKDIR /app

EXPOSE 8080

ADD target/ecommerce-3.13.2.jar .

CMD ["java", "-jar", "ecommerce-3.13.2.jar"]