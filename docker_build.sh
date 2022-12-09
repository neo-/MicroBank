#! /bin/bash

mvn -f Microservices/accounts/pom.xml spring-boot:build-image -DskipTests
mvn -f Microservices/cards/pom.xml spring-boot:build-image -DskipTests
mvn -f Microservices/config-server/pom.xml spring-boot:build-image -DskipTests
mvn -f Microservices/eureka-server/pom.xml spring-boot:build-image -DskipTests
mvn -f Microservices/gateway-server/pom.xml spring-boot:build-image -DskipTests
mvn -f Microservices/loans/pom.xml spring-boot:build-image -DskipTests
